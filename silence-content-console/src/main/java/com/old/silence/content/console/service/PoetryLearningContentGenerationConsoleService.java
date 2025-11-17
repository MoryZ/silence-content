package com.old.silence.content.console.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.PoetryCategoryClient;
import com.old.silence.content.api.PoetryGradeClient;
import com.old.silence.content.api.PromptCommonFormatClient;
import com.old.silence.content.api.PromptTemplateClient;
import com.old.silence.content.console.api.config.llm.OllamaClient;
import com.old.silence.content.console.dto.PoetryLearningContentConsoleCommand;
import com.old.silence.content.console.vo.PoetryCategoryConsoleView;
import com.old.silence.content.console.vo.PoetryGradeConsoleView;
import com.old.silence.content.console.vo.PromptCommonFormatConsoleView;
import com.old.silence.content.console.vo.PromptTemplateConsoleView;
import com.old.silence.content.domain.enums.PromptFormatType;
import com.old.silence.content.domain.enums.PromptTemplateType;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 诗词题目生成服务
 * 使用 Ollama 根据学习内容生成练习题
 */
@Service
public class PoetryLearningContentGenerationConsoleService {

    private static final Logger log = LoggerFactory.getLogger(PoetryLearningContentGenerationConsoleService.class);

    private final PoetryGradeClient poetryGradeClient;
    private final PoetryCategoryClient poetryCategoryClient;
    private final PromptTemplateClient promptTemplateClient;
    private final PromptCommonFormatClient promptCommonFormatClient;
    private final OllamaClient ollamaClient;
    private final JacksonMapper jacksonMapper;


    public PoetryLearningContentGenerationConsoleService(PoetryGradeClient poetryGradeClient,
                                                         PoetryCategoryClient poetryCategoryClient,
                                                         PromptTemplateClient promptTemplateClient,
                                                         PromptCommonFormatClient promptCommonFormatClient,
                                                         OllamaClient ollamaClient, JacksonMapper jacksonMapper) {
        this.poetryGradeClient = poetryGradeClient;
        this.poetryCategoryClient = poetryCategoryClient;
        this.promptTemplateClient = promptTemplateClient;
        this.promptCommonFormatClient = promptCommonFormatClient;
        this.ollamaClient = ollamaClient;
        this.jacksonMapper = jacksonMapper;
    }

    /**
     * 批量生成学习内容：读取所有分类并生成学习材料
     */
    public List<PoetryLearningContentConsoleCommand> generateLearningContentForAllSubCategoryIds(List<BigInteger> subCategoryIds, BigInteger poetryGradeId) {
        log.info("开始批量生成学习内容...");

        List<PoetryCategoryConsoleView> categories = poetryCategoryClient.findByIds(subCategoryIds, PoetryCategoryConsoleView.class);
        log.info("找到 {} 个分类", categories.size());

        var poetryGrade = findPoetryGradeById(poetryGradeId);
        log.info("找到年级：{}", poetryGrade.getName());

        List<PoetryLearningContentConsoleCommand> allLearningContents = new ArrayList<>();
        for (PoetryCategoryConsoleView category : categories) {
            try {
                List<PoetryLearningContentConsoleCommand> learningContents = generateLearningContentForCategory(category, poetryGrade.getName());
                log.info("为分类 ID: {} (名称: {}) 生成了 {} 条学习内容",
                        category.getId(), category.getName(), CollectionUtils.size(learningContents));
                allLearningContents.addAll(learningContents);
            } catch (Exception e) {
                log.error("为分类 ID: {} 生成学习内容时出错: {}", category.getId(), e.getMessage(), e);
            }
        }

        log.info("批量生成完成，共生成 {} 条学习内容", CollectionUtils.size(allLearningContents));
        return allLearningContents;
    }

    /**
     * 为单个分类生成学习内容
     */
    public List<PoetryLearningContentConsoleCommand> generateLearningContentForSubCategoryId(BigInteger categoryId, BigInteger poetryGradeId) {
        PoetryCategoryConsoleView category = poetryCategoryClient
                .findById(categoryId, PoetryCategoryConsoleView.class)
                .orElseThrow(() -> new IllegalArgumentException("分类不存在: " + categoryId));

        var poetryGrade = findPoetryGradeById(poetryGradeId);
        log.info("找到年级：{}", poetryGrade.getName());
        return generateLearningContentForCategory(category, poetryGrade.getName());
    }

    /**
     * 为单个分类生成学习内容
     */
    public List<PoetryLearningContentConsoleCommand> generateLearningContentForCategory(PoetryCategoryConsoleView category, String gradeLevel) {
        log.debug("开始为分类生成学习内容: ID={}, 名称={}", category.getId(), category.getName());

        // 构建提示词
        String prompt = buildLearningPrompt(category, gradeLevel);

        // 调用 Ollama 生成学习内容
        String generatedText = ollamaClient.invokeOllama(prompt);
        log.debug("Ollama 生成结果: {}", generatedText);

        // 解析生成的学习内容
        LearningContentData learningContentData = parseGeneratedLearningContent(generatedText);

        // 转换为实体对象
        return createLearningContent(learningContentData, category);
    }

    private PoetryGradeConsoleView findPoetryGradeById(BigInteger poetryGradeId) {
        return poetryGradeClient.findById(poetryGradeId, PoetryGradeConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private List<PoetryLearningContentConsoleCommand> createLearningContent(LearningContentData contentData, PoetryCategoryConsoleView category) {
        try {
            return convertToLearningContentEntity(category.getId(), category.getParentId(), contentData);
        } catch (Exception e) {
            log.error("解析生成的学习内容失败: {}", e.getMessage(), e);
            throw new RuntimeException("解析学习内容失败: " + e.getMessage(), e);
        }
    }

    /**
     * 构建学习内容提示词
     */
    private String buildLearningPrompt(PoetryCategoryConsoleView category, String gradeLevel) {
        // 获取对应子分类的提示词模板
        var promptTemplateOptional = promptTemplateClient.findBySubCategoryIdAndTemplateType(category.getId(), PromptTemplateType.LEARNING_CONTENT,
                PromptTemplateConsoleView.class);

        if (promptTemplateOptional.isEmpty()) {
            // 如果没有找到模板，使用默认的学习内容生成模板
            return buildDefaultLearningPrompt(category);
        }

        // 使用模板构建提示词
        PromptTemplateConsoleView template = promptTemplateOptional.get();
        String commonFormat = getCommonFormat();
        Map<String, String> variables = buildLearningVariables(category, gradeLevel, commonFormat);

        return renderTemplate(template.getTemplateContent(), variables);
    }

    /**
     * 构建默认的学习内容提示词
     */
    private String buildDefaultLearningPrompt(PoetryCategoryConsoleView category) {
        StringBuilder sb = new StringBuilder();
        sb.append("请根据以下分类信息，生成详细的学习内容。学习内容应该包括：\n\n");
        sb.append("1. **知识概述**：简要介绍该分类的核心知识点\n");
        sb.append("2. **详细解析**：对重要概念和内容的深入讲解\n");
        sb.append("3. **实例说明**：通过具体例子帮助理解\n");
        sb.append("4. **学习方法**：提供有效的学习策略和技巧\n");
        sb.append("5. **常见问题**：解答学习者可能遇到的疑问\n");
        sb.append("6. **拓展知识**：相关的延伸阅读和知识链接\n");
        sb.append("7. **总结回顾**：关键知识点的归纳总结\n");
        sb.append("8. **自我检测**：学习效果的自我评估方法\n\n");

        sb.append("分类信息：\n");
        sb.append("分类名称：").append(category.getName()).append("\n");
        sb.append("分类代码：").append(category.getCode()).append("\n");
        if (category.getParentId() != null) {
            sb.append("父分类ID：").append(category.getParentId()).append("\n");
        }

        sb.append("\n请生成详细的学习内容，返回JSON格式，格式如下：\n");
        sb.append("{\n");
        sb.append("  \"title\": \"学习内容标题\",\n");
        sb.append("  \"subtitle\": \"学习内容副标题\",\n");
        sb.append("  \"contentType\": 1,  // 内容类型：1-诗词, 2-文言文, 3-语法知识\n");
        sb.append("  \"originalText\": \"原文内容（如果有）\",\n");
        sb.append("  \"author\": \"作者（如果有）\",\n");
        sb.append("  \"dynasty\": \"朝代（如果有）\",\n");
        sb.append("  \"explanation\": \"详细的知识讲解和解析\",\n");
        sb.append("  \"usageExamples\": \"用法示例和例句\",\n");
        sb.append("  \"annotations\": \"注释和说明\",\n");
        sb.append("  \"translation\": \"翻译（如果有）\",\n");
        sb.append("  \"appreciation\": \"赏析和评价\",\n");
        sb.append("  \"background\": \"背景知识和历史 context\",\n");
        sb.append("  \"difficulty\": 2,  // 难度等级（1-5）\n");
        sb.append("  \"keyPoints\": [\"关键知识点1\", \"关键知识点2\"],  // 关键知识点数组\n");
        sb.append("  \"learningTips\": [\"学习技巧1\", \"学习技巧2\"]  // 学习技巧数组\n");
        sb.append("}\n");

        sb.append("\n注意：\n");
        sb.append("1. 内容要详细、准确、易懂\n");
        sb.append("2. 根据分类特点生成合适的内容\n");
        sb.append("3. 只返回JSON对象，不要其他文字说明\n");

        return sb.toString();
    }

    private Map<String, String> buildLearningVariables(PoetryCategoryConsoleView category, String gradeLevel, String commonFormat) {
        Map<String, String> variables = new HashMap<>();
        variables.put("name", category.getName());
        variables.put("commonFormat", commonFormat);
        variables.put("gradeLevel", gradeLevel);
        return variables;
    }

    private String renderTemplate(String template, Map<String, String> variables) {
        String result = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("${" + entry.getKey() + "}",
                    entry.getValue() != null ? entry.getValue() : "");
        }
        return result;
    }

    private String getCommonFormat() {
        var promptCommonFormatOptional = promptCommonFormatClient.findByFormatType(PromptFormatType.LEARNING_CONTENT, PromptCommonFormatConsoleView.class);
        return promptCommonFormatOptional.map(PromptCommonFormatConsoleView::getFormatContent).orElseGet(this::getDefaultFormat);
    }

    private String getDefaultFormat() {
        return "\n请返回JSON对象格式，包含详细的学习内容信息...";
    }

    /**
     * 解析生成的学习内容JSON
     */
    private LearningContentData parseGeneratedLearningContent(String generatedText) {
        try {
            // 清理和提取JSON
            String jsonText = cleanAndExtractJson(generatedText);

            // 解析JSON
            return jacksonMapper.unwrap().readValue(jsonText, LearningContentData.class);
        } catch (Exception e) {
            log.error("解析学习内容JSON失败，原始文本: {}", generatedText);
            throw new RuntimeException("解析生成的学习内容JSON失败: " + e.getMessage(), e);
        }
    }

    private String cleanAndExtractJson(String text) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }

        String cleaned = text.trim();
        log.info("原始输入长度: {}", cleaned.length());

        try {
            // 首先尝试直接验证，如果是有效JSON就直接返回
            if (jacksonMapper.validateJson(cleaned)) {
                log.info("原始文本本身就是有效JSON，无需清理");
                return cleaned;
            }
        } catch (Exception e) {
            log.info("原始文本不是有效JSON，开始清理流程: {}", e.getMessage());
        }

        // 处理各种markdown代码块格式
        String[] prefixes = {"```json", "```"};
        for (String prefix : prefixes) {
            if (cleaned.startsWith(prefix)) {
                cleaned = cleaned.substring(prefix.length());
                log.info("移除前缀: {}", prefix);
                break;
            }
        }

        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
            log.info("移除 ``` 后缀");
        }

        cleaned = cleaned.trim();

        // 查找JSON对象边界
        int objectStart = cleaned.indexOf('{');
        int objectEnd = cleaned.lastIndexOf('}');

        if (objectStart >= 0 && objectEnd > objectStart) {
            String jsonContent = cleaned.substring(objectStart, objectEnd + 1);
            log.info("提取JSON对象，长度: {}", jsonContent.length());

            // 尝试修复常见的JSON语法错误
            String fixedJson = fixCommonJsonErrors(jsonContent);

            try {
                if (jacksonMapper.validateJson(fixedJson)) {
                    log.info("✅ 修复后的JSON验证成功");
                    return fixedJson;
                }
            } catch (Exception e) {
                log.warn("修复后的JSON验证失败: {}", e.getMessage());

                // 再尝试原始JSON
                try {
                    if (jacksonMapper.validateJson(jsonContent)) {
                        log.info("✅ 原始JSON验证成功");
                        return jsonContent;
                    }
                } catch (Exception e2) {
                    log.warn("原始JSON验证也失败: {}", e2.getMessage());
                }
            }
        }

        log.warn("无法提取有效JSON，返回清理后的文本");
        return cleaned;
    }

    /**
     * 修复常见的JSON语法错误
     */
    private String fixCommonJsonErrors(String json) {
        if (json == null) return null;

        String fixed = json;

        // 修复1: 在引号后花括号前缺少逗号的情况
        // 模式: "...}"\s*"  ->  "...},"
        fixed = fixed.replaceAll("(\"\\s*)\\n\\s*\"", "$1,\\n\"");

        // 修复2: 在值后直接跟新属性缺少逗号的情况
        // 模式: "...值"\s*"新属性" -> "...值", "新属性"
        fixed = fixed.replaceAll("([^\\{\\},]\")\\s*\\n\\s*\"", "$1,\\n\"");

        // 修复3: 在字符串值后直接跟新行和属性
        fixed = fixed.replaceAll("(\"[^\"]*\")\\s*\\n\\s*\"", "$1,\\n\"");

        // 修复4: 移除可能的多余逗号
        fixed = fixed.replaceAll(",\\s*\\}", "}");
        fixed = fixed.replaceAll(",\\s*\\]", "]");

        log.info("JSON修复前: {}", json.length() > 100 ? json.substring(0, 100) + "..." : json);
        log.info("JSON修复后: {}", fixed.length() > 100 ? fixed.substring(0, 100) + "..." : fixed);

        return fixed;
    }

    /**
     * 转换为学习内容实体对象
     */
    private List<PoetryLearningContentConsoleCommand> convertToLearningContentEntity(BigInteger subCategoryId, BigInteger categoryId, LearningContentData contentData) {
        PoetryLearningContentConsoleCommand content = new PoetryLearningContentConsoleCommand();

        // 设置基本属性
        content.setTitle(contentData.title);
        content.setSubtitle(contentData.subtitle);
        content.setContentType(contentData.contentType != null ? contentData.contentType.longValue() : 1L);
        content.setCategoryId(categoryId);
        content.setSubCategoryId(subCategoryId);
        content.setDifficulty(contentData.difficulty != null ? contentData.difficulty.longValue() : 2L);

        // 设置内容相关字段
        content.setOriginalText(contentData.originalText);
        content.setAuthor(contentData.author);
        content.setDynasty(contentData.dynasty);
        content.setExplanation(contentData.explanation);
        content.setUsageExamples(jacksonMapper.toJson(contentData.usageExamples));
        content.setAnnotations(jacksonMapper.toJson(contentData.annotations));
        content.setTranslation(contentData.translation);
        content.setAppreciation(contentData.appreciation);
        content.setBackground(contentData.background);

        // 设置默认值
        content.setGradeId(BigInteger.ONE); // 默认年级
        content.setViewCount(0L);
        content.setEnabled(true);

        return Collections.singletonList(content);
    }

    /**
     * 学习内容数据结构
     */
    public static class LearningContentData {
        private String title;
        private String subtitle;
        private Integer contentType;
        private Integer gradeId;        // 新增
        private Integer categoryId;     // 新增
        private Integer subCategoryId;  // 新增
        private String originalText;
        private String author;
        private String dynasty;
        private String explanation;
        private List<String> usageExamples;  // 改为 List
        private List<String> annotations;    // 改为 List
        private String translation;
        private String appreciation;
        private String background;
        private Integer difficulty;
        private List<String> keyPoints;
        private List<String> learningObjectives;  // 新增
        private String extendedKnowledge;         // 新增
        private String audioScript;               // 新增
        private String imageDescription;          // 新增

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public Integer getContentType() {
            return contentType;
        }

        public void setContentType(Integer contentType) {
            this.contentType = contentType;
        }

        public Integer getGradeId() {
            return gradeId;
        }

        public void setGradeId(Integer gradeId) {
            this.gradeId = gradeId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(Integer subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getOriginalText() {
            return originalText;
        }

        public void setOriginalText(String originalText) {
            this.originalText = originalText;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDynasty() {
            return dynasty;
        }

        public void setDynasty(String dynasty) {
            this.dynasty = dynasty;
        }

        public String getExplanation() {
            return explanation;
        }

        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }

        public List<String> getUsageExamples() {
            return usageExamples;
        }

        public void setUsageExamples(List<String> usageExamples) {
            this.usageExamples = usageExamples;
        }

        public List<String> getAnnotations() {
            return annotations;
        }

        public void setAnnotations(List<String> annotations) {
            this.annotations = annotations;
        }

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }

        public String getAppreciation() {
            return appreciation;
        }

        public void setAppreciation(String appreciation) {
            this.appreciation = appreciation;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public Integer getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(Integer difficulty) {
            this.difficulty = difficulty;
        }

        public List<String> getKeyPoints() {
            return keyPoints;
        }

        public void setKeyPoints(List<String> keyPoints) {
            this.keyPoints = keyPoints;
        }

        public List<String> getLearningObjectives() {
            return learningObjectives;
        }

        public void setLearningObjectives(List<String> learningObjectives) {
            this.learningObjectives = learningObjectives;
        }

        public String getExtendedKnowledge() {
            return extendedKnowledge;
        }

        public void setExtendedKnowledge(String extendedKnowledge) {
            this.extendedKnowledge = extendedKnowledge;
        }

        public String getAudioScript() {
            return audioScript;
        }

        public void setAudioScript(String audioScript) {
            this.audioScript = audioScript;
        }

        public String getImageDescription() {
            return imageDescription;
        }

        public void setImageDescription(String imageDescription) {
            this.imageDescription = imageDescription;
        }
    }


}

