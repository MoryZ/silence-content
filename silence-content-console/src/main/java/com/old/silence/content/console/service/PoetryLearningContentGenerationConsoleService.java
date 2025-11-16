package com.old.silence.content.console.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.PoetryCategoryClient;
import com.old.silence.content.api.PromptCommonFormatClient;
import com.old.silence.content.api.PromptTemplateClient;
import com.old.silence.content.console.api.config.llm.OllamaClient;
import com.old.silence.content.console.dto.PoetryLearningContentConsoleCommand;
import com.old.silence.content.console.vo.PoetryCategoryConsoleView;
import com.old.silence.content.console.vo.PromptCommonFormatConsoleView;
import com.old.silence.content.console.vo.PromptTemplateConsoleView;
import com.old.silence.content.domain.enums.PromptTemplateType;
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

    private final PoetryCategoryClient poetryCategoryClient;
    private final PromptTemplateClient promptTemplateClient;
    private final PromptCommonFormatClient promptCommonFormatClient;
    private final OllamaClient ollamaClient;
    private final JacksonMapper jacksonMapper;


    public PoetryLearningContentGenerationConsoleService(PoetryCategoryClient poetryCategoryClient,
                                                         PromptTemplateClient promptTemplateClient,
                                                         PromptCommonFormatClient promptCommonFormatClient,
                                                         OllamaClient ollamaClient, JacksonMapper jacksonMapper) {
        this.poetryCategoryClient = poetryCategoryClient;
        this.promptTemplateClient = promptTemplateClient;
        this.promptCommonFormatClient = promptCommonFormatClient;
        this.ollamaClient = ollamaClient;
        this.jacksonMapper = jacksonMapper;
    }

    /**
     * 批量生成学习内容：读取所有分类并生成学习材料
     */
    public List<PoetryLearningContentConsoleCommand> generateLearningContentForAllSubCategoryIds(List<BigInteger> subCategoryIds) {
        log.info("开始批量生成学习内容...");

        List<PoetryCategoryConsoleView> categories = poetryCategoryClient.findByIds(subCategoryIds, PoetryCategoryConsoleView.class);
        log.info("找到 {} 个分类", categories.size());

        List<PoetryLearningContentConsoleCommand> allLearningContents = new ArrayList<>();
        for (PoetryCategoryConsoleView category : categories) {
            try {
                List<PoetryLearningContentConsoleCommand> learningContents = generateLearningContentForCategory(category);
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
    public List<PoetryLearningContentConsoleCommand> generateLearningContentForSubCategoryId(BigInteger categoryId) {
        PoetryCategoryConsoleView category = poetryCategoryClient
                .findById(categoryId, PoetryCategoryConsoleView.class)
                .orElseThrow(() -> new IllegalArgumentException("分类不存在: " + categoryId));
        return generateLearningContentForCategory(category);
    }

    /**
     * 为单个分类生成学习内容
     */
    public List<PoetryLearningContentConsoleCommand> generateLearningContentForCategory(PoetryCategoryConsoleView category) {
        log.debug("开始为分类生成学习内容: ID={}, 名称={}", category.getId(), category.getName());

        // 构建提示词
        String prompt = buildLearningPrompt(category);

        // 调用 Ollama 生成学习内容
        String generatedText = ollamaClient.invokeOllama(prompt);
        log.debug("Ollama 生成结果: {}", generatedText);

        // 解析生成的学习内容
        LearningContentData learningContentData = parseGeneratedLearningContent(generatedText);

        // 转换为实体对象
        return createLearningContent(learningContentData, category);
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
    private String buildLearningPrompt(PoetryCategoryConsoleView category) {
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
        Map<String, String> variables = buildLearningVariables(category, commonFormat);

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

    private Map<String, String> buildLearningVariables(PoetryCategoryConsoleView category, String commonFormat) {
        Map<String, String> variables = new HashMap<>();
        variables.put("title", category.getName());
        variables.put("name", category.getName());
        variables.put("code", category.getCode());
        variables.put("parentId", category.getParentId() != null ? category.getParentId().toString() : "");
        variables.put("icon", category.getIcon() != null ? category.getIcon() : "");
        variables.put("sortOrder", category.getSortOrder() != null ? category.getSortOrder().toString() : "");
        variables.put("commonFormat", commonFormat);
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
        var promptCommonFormatOptional = promptCommonFormatClient.findByActive(true, PromptCommonFormatConsoleView.class);
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
        // 移除可能的markdown代码块标记
        String cleaned = text.trim();
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        }
        if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3);
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }
        cleaned = cleaned.trim();

        // 提取JSON对象（{...}）
        int startIdx = cleaned.indexOf('{');
        int endIdx = cleaned.lastIndexOf('}');
        if (startIdx >= 0 && endIdx > startIdx) {
            cleaned = cleaned.substring(startIdx, endIdx + 1);
        }

        return cleaned;
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
        content.setUsageExamples(contentData.usageExamples);
        content.setAnnotations(contentData.annotations);
        content.setTranslation(contentData.translation);
        content.setAppreciation(contentData.appreciation);
        content.setBackground(contentData.background);

        // 设置默认值
        content.setGradeId(BigInteger.ONE); // 默认年级
        content.setViewCount(0L);
        content.setEnabled(true);

        // 处理数组字段（keyPoints, learningTips）
        if (contentData.keyPoints != null && !contentData.keyPoints.isEmpty()) {
            content.setAnnotations(String.join("; ", contentData.keyPoints));
        }

        if (contentData.learningTips != null && !contentData.learningTips.isEmpty()) {
            content.setUsageExamples(String.join("; ", contentData.learningTips));
        }

        return Collections.singletonList(content);
    }

    /**
     * 学习内容数据结构
     */
    public static class LearningContentData {
        private String title;
        private String subtitle;
        private Integer contentType;
        private String originalText;
        private String author;
        private String dynasty;
        private String explanation;
        private String usageExamples;
        private String annotations;
        private String translation;
        private String appreciation;
        private String background;
        private Integer difficulty;
        private List<String> keyPoints;
        private List<String> learningTips;

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

        public String getUsageExamples() {
            return usageExamples;
        }

        public void setUsageExamples(String usageExamples) {
            this.usageExamples = usageExamples;
        }

        public String getAnnotations() {
            return annotations;
        }

        public void setAnnotations(String annotations) {
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

        public List<String> getLearningTips() {
            return learningTips;
        }

        public void setLearningTips(List<String> learningTips) {
            this.learningTips = learningTips;
        }
    }


}

