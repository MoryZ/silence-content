package com.old.silence.content.console.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.old.silence.content.api.PoetryLearningContentClient;
import com.old.silence.content.api.PromptCommonFormatClient;
import com.old.silence.content.api.PromptTemplateClient;
import com.old.silence.content.console.api.config.llm.LlmClient;
import com.old.silence.content.console.api.config.llm.model.QuestionData;
import com.old.silence.content.console.dto.PoetryQuizQuestionsConsoleCommand;
import com.old.silence.content.console.vo.PoetryLearningContentConsoleView;
import com.old.silence.content.console.vo.PromptCommonFormatConsoleView;
import com.old.silence.content.console.vo.PromptTemplateConsoleView;
import com.old.silence.content.domain.enums.QuestionType;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 诗词题目生成服务
 * 使用 Ollama 根据学习内容生成练习题
 */
@Service
public class PoetryQuizQuestionGenerationConsoleService {

    private static final Logger log = LoggerFactory.getLogger(PoetryQuizQuestionGenerationConsoleService.class);

    private final PoetryLearningContentClient poetryLearningContentClient;
    private final PromptTemplateClient promptTemplateClient;
    private final PromptCommonFormatClient promptCommonFormatClient;
    private final LlmClient llmClient;
    private final JacksonMapper jacksonMapper;


    public PoetryQuizQuestionGenerationConsoleService(PoetryLearningContentClient poetryLearningContentClient,
                                                      PromptTemplateClient promptTemplateClient,
                                                      PromptCommonFormatClient promptCommonFormatClient,
                                                      LlmClient llmClient, JacksonMapper jacksonMapper) {
        this.poetryLearningContentClient = poetryLearningContentClient;
        this.promptTemplateClient = promptTemplateClient;
        this.promptCommonFormatClient = promptCommonFormatClient;
        this.llmClient = llmClient;
        this.jacksonMapper = jacksonMapper;
    }

    private static String buildDefaultQuizQuestionsPrompt(PoetryLearningContentConsoleView content, List<PromptTemplateConsoleView> promptTemplates) {

        StringBuilder sb = new StringBuilder();
        sb.append("请根据以下诗词学习内容，生成5道练习题。题目难度要合理分配，可以是1-2道简单题（难度1-2），2-3道中等题（难度3-4），0-1道困难题（难度5）。\n\n");
        sb.append("学习内容信息：\n");
        sb.append("标题：").append(content.getTitle()).append("\n");
        if (content.getAuthor() != null) {
            sb.append("作者：").append(content.getAuthor()).append("\n");
        }
        if (content.getDynasty() != null) {
            sb.append("朝代：").append(content.getDynasty()).append("\n");
        }
        if (content.getOriginalText() != null) {
            sb.append("原文：\n").append(content.getOriginalText()).append("\n");
        }
        if (content.getTranslation() != null) {
            sb.append("译文：\n").append(content.getTranslation()).append("\n");
        }
        if (content.getExplanation() != null) {
            sb.append("解析：\n").append(content.getExplanation()).append("\n");
        }

        sb.append("\n请生成5道题目，返回JSON格式，格式如下：\n");
        sb.append("[\n");
        sb.append("  {\n");
        sb.append("    \"questionType\": \"FILL_BLANK\",  // 题型：SINGLE_CHOICE(单选), MULTI_CHOICE(多选), FILL_BLANK(填空), ORDER_SORT(排序), TRUE_FALSE(判断)\n");
        sb.append("    \"questionStem\": \"床前明月光，疑是地上霜。举头望明月，__________。\",  // 题目主干\n");
        sb.append("    \"questionData\": {\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡\", \"低头思故乡。\"]},  // 题目动态数据（JSON对象）\n");
        sb.append("    \"correctAnswer\": {\"text\": \"低头思故乡\"},  // 标准答案（JSON对象）\n");
        sb.append("    \"explanation\": \"这是《静夜思》的最后一句，通过\\\"低头\\\"与\\\"举头\\\"的对比，表达思乡之情。\",  // 解析\n");
        sb.append("    \"difficulty\": 1,  // 难度等级（1-5）\n");
        sb.append("    \"hints\": [\"首字：低\"]  // 提示信息（数组）\n");
        sb.append("  }\n");
        sb.append("]\n");
        sb.append("\n注意：\n");
        sb.append("1. questionType 必须是：SINGLE_CHOICE, MULTI_CHOICE, FILL_BLANK, ORDER_SORT, TRUE_FALSE 之一\n");
        sb.append("2. questionData 和 correctAnswer 必须是有效的JSON对象\n");
        sb.append("3. hints 是字符串数组\n");
        sb.append("4. 难度要合理分配，总共5道题\n");
        sb.append("5. 只返回JSON数组，不要其他文字说明\n");

        return sb.toString();
    }

    /**
     * 批量生成题目：读取所有学习内容并生成题目
     */
    public List<PoetryQuizQuestionsConsoleCommand> generateQuestionsForAllContents(List<BigInteger> contentIds) {
        log.info("开始批量生成题目...");


        List<PoetryLearningContentConsoleView> contents = poetryLearningContentClient.findByIds(contentIds, PoetryLearningContentConsoleView.class);
        log.info("找到 {} 条学习内容", contents.size());
        List<PoetryQuizQuestionsConsoleCommand> allPoetryQuizQuestions = new ArrayList<>();
        for (PoetryLearningContentConsoleView content : contents) {
            try {
                List<PoetryQuizQuestionsConsoleCommand> poetryQuizQuestions = generateQuestionsForContent(content);
                log.info("为内容 ID: {} (标题: {}) 生成了 {} 道题目",
                        content.getId(), content.getTitle(), CollectionUtils.size(poetryQuizQuestions));
                allPoetryQuizQuestions.addAll(poetryQuizQuestions);
            } catch (Exception e) {
                log.error("为内容 ID: {} 生成题目时出错: {}", content.getId(), e.getMessage(), e);
            }
        }

        log.info("批量生成完成，共生成 {} 道题目", CollectionUtils.size(allPoetryQuizQuestions));
        return allPoetryQuizQuestions;
    }

    /**
     * 为单个学习内容生成题目
     */
    public List<PoetryQuizQuestionsConsoleCommand> generateQuestionsForContent(BigInteger contentId) {
        PoetryLearningContentConsoleView content = poetryLearningContentClient
                .findById(contentId, PoetryLearningContentConsoleView.class)
                .orElseThrow(() -> new IllegalArgumentException("学习内容不存在: " + contentId));
        return generateQuestionsForContent(content);
    }

    /**
     * 为单个学习内容生成题目
     */
    public List<PoetryQuizQuestionsConsoleCommand> generateQuestionsForContent(PoetryLearningContentConsoleView content) {
        log.debug("开始为内容生成题目: ID={}, 标题={}", content.getId(), content.getTitle());

        // 构建提示词
        var prompt = buildPrompt(content);

        // 调用 LLM 生成题目
        String generatedText = llmClient.invokeLlm(prompt);
        log.debug("LLM 生成结果: {}", generatedText);

        // 解析生成的 JSON
        var questionDatas = parseGeneratedQuestions(generatedText);
        return CollectionUtils.transformToList(questionDatas, questionData -> createQuestion(questionData, content));
    }

    private PoetryQuizQuestionsConsoleCommand createQuestion(QuestionData questionData, PoetryLearningContentConsoleView content) {
        try {
            return convertToEntity(content.getId(), questionData);
        } catch (Exception e) {
            log.error("解析生成的题目失败: {}", e.getMessage(), e);
            throw new RuntimeException("解析题目失败: " + e.getMessage(), e);
        }

    }

    /**
     * 构建提示词
     */
    private String buildPrompt(PoetryLearningContentConsoleView content) {

        var promptTemplates = promptTemplateClient.findBySubCategoryId(content.getSubCategoryId(), PromptTemplateConsoleView.class);

        if (CollectionUtils.isEmpty(promptTemplates)) {
            return buildDefaultQuizQuestionsPrompt(content, promptTemplates);
        }

        // 1. 获取对应的模板
        PromptTemplateConsoleView template = promptTemplates.getFirst();

        // 2. 获取通用格式
        String commonFormat = getCommonFormat();

        // 3. 构建变量映射
        Map<String, String> variables = buildVariables(content, commonFormat);

        // 4. 渲染模板
        return renderTemplate(template.getTemplateContent(), variables);


    }

    private Map<String, String> buildVariables(PoetryLearningContentConsoleView content, String commonFormat) {
        Map<String, String> variables = new HashMap<>();
        variables.put("title", content.getTitle());
        variables.put("author", content.getAuthor() != null ? content.getAuthor() : "");
        variables.put("dynasty", content.getDynasty() != null ? content.getDynasty() : "");
        variables.put("originalText", content.getOriginalText() != null ? content.getOriginalText() : "");
        variables.put("translation", content.getTranslation() != null ? content.getTranslation() : "");
        variables.put("appreciation", content.getAppreciation() != null ? content.getAppreciation() : "");
        variables.put("explanation", content.getExplanation() != null ? content.getExplanation() : "");
        variables.put("annotation", content.getAnnotations() != null ? content.getAnnotations() : "");
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
        return "\n请返回JSON数组格式..."; // 默认格式
    }

    /**
     * 解析生成的题目JSON
     */
    private List<QuestionData> parseGeneratedQuestions(String generatedText) {
        try {
            // 尝试提取JSON部分（去除可能的markdown代码块标记）
            String jsonText = generatedText.trim();
            if (jsonText.startsWith("```json")) {
                jsonText = jsonText.substring(7);
            }
            if (jsonText.startsWith("```")) {
                jsonText = jsonText.substring(3);
            }
            if (jsonText.endsWith("```")) {
                jsonText = jsonText.substring(0, jsonText.length() - 3);
            }
            jsonText = jsonText.trim();

            // 查找第一个 [ 和最后一个 ]
            int startIdx = jsonText.indexOf('[');
            int endIdx = jsonText.lastIndexOf(']');
            if (startIdx >= 0 && endIdx > startIdx) {
                jsonText = jsonText.substring(startIdx, endIdx + 1);
            }

            // 移除所有单行注释（// 开头的注释）
            jsonText = removeJsonComments(jsonText);

            // 尝试解析完整的 JSON
            try {
                List<QuestionData> allQuestions = jacksonMapper.unwrap().readValue(jsonText, new TypeReference<List<QuestionData>>() {
                });
                // 只取前5道题目，并去重
                return deduplicateAndLimit(allQuestions, 5);
            } catch (Exception e) {
                // 如果完整解析失败，尝试提取有效的部分
                log.warn("完整JSON解析失败，尝试提取有效部分: {}", e.getMessage());
                return parsePartialJson(jsonText);
            }
        } catch (Exception e) {
            log.error("解析JSON失败，原始文本: {}", generatedText);
            throw new RuntimeException("解析生成的题目JSON失败: " + e.getMessage(), e);
        }
    }


    /**
     * 解析部分JSON（处理被截断的情况）
     */
    private List<QuestionData> parsePartialJson(String jsonText) {
        List<QuestionData> questions = new ArrayList<>();
        int startIdx = jsonText.indexOf('[');
        if (startIdx < 0) {
            return questions;
        }

        // 尝试逐个提取完整的对象
        int currentPos = startIdx + 1;
        int braceCount = 0;
        int bracketCount = 1; // 已经有一个 [
        StringBuilder currentObject = new StringBuilder();
        boolean inString = false;
        boolean escaped = false;

        while (currentPos < jsonText.length() && questions.size() < 5) {
            char c = jsonText.charAt(currentPos);

            if (escaped) {
                currentObject.append(c);
                escaped = false;
                currentPos++;
                continue;
            }

            if (c == '\\') {
                escaped = true;
                currentObject.append(c);
                currentPos++;
                continue;
            }

            if (c == '"') {
                inString = !inString;
                currentObject.append(c);
                currentPos++;
                continue;
            }

            if (inString) {
                currentObject.append(c);
                currentPos++;
                continue;
            }

            if (c == '{') {
                braceCount++;
                currentObject.append(c);
            } else if (c == '}') {
                braceCount--;
                currentObject.append(c);
                if (braceCount == 0) {
                    // 找到一个完整的对象
                    try {
                        String objJson = "[" + currentObject + "]";
                        List<QuestionData> parsed = jacksonMapper.unwrap().readValue(objJson, new TypeReference<List<QuestionData>>() {
                        });
                        questions.addAll(parsed);
                    } catch (Exception e) {
                        log.warn("解析单个对象失败: {}", e.getMessage());
                    }
                    currentObject.setLength(0);
                    // 跳过逗号和空白
                    while (currentPos + 1 < jsonText.length() &&
                            (jsonText.charAt(currentPos + 1) == ',' ||
                                    Character.isWhitespace(jsonText.charAt(currentPos + 1)))) {
                        currentPos++;
                    }
                } else {
                    currentObject.append(c);
                }
            } else if (c == '[') {
                bracketCount++;
                if (braceCount > 0) {
                    currentObject.append(c);
                }
            } else if (c == ']') {
                bracketCount--;
                if (braceCount > 0) {
                    currentObject.append(c);
                }
                if (bracketCount == 0) {
                    break;
                }
            } else {
                if (braceCount > 0) {
                    currentObject.append(c);
                }
            }
            currentPos++;
        }

        return deduplicateAndLimit(questions, 5);
    }

    /**
     * 去重并限制题目数量
     */
    private List<QuestionData> deduplicateAndLimit(List<QuestionData> questions, int maxCount) {
        if (questions == null || questions.isEmpty()) {
            return new ArrayList<>();
        }

        List<QuestionData> result = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        for (QuestionData question : questions) {
            if (question == null || question.questionStem == null) {
                continue;
            }

            // 使用题目主干作为去重键
            String key = question.questionType + "|" + question.questionStem;
            if (!seen.contains(key) && result.size() < maxCount) {
                seen.add(key);
                result.add(question);
            }
        }

        return result;
    }

    /**
     * 移除 JSON 中的注释
     * 处理单行注释（//）和行尾注释
     */
    private String removeJsonComments(String jsonText) {
        StringBuilder result = new StringBuilder();
        String[] lines = jsonText.split("\n");
        boolean inString = false;
        boolean escaped = false;

        for (String line : lines) {
            StringBuilder cleanedLine = new StringBuilder();
            char[] chars = line.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];

                // 处理转义字符
                if (escaped) {
                    cleanedLine.append(c);
                    escaped = false;
                    continue;
                }

                if (c == '\\') {
                    escaped = true;
                    cleanedLine.append(c);
                    continue;
                }

                // 处理字符串边界
                if (c == '"') {
                    inString = !inString;
                    cleanedLine.append(c);
                    continue;
                }

                // 在字符串内部，保留所有字符
                if (inString) {
                    cleanedLine.append(c);
                    continue;
                }

                // 在字符串外部，检查是否是注释开始
                if (c == '/' && i + 1 < chars.length && chars[i + 1] == '/') {
                    // 遇到单行注释，停止处理这一行的剩余部分
                    break;
                }

                cleanedLine.append(c);
            }

            // 移除行尾空白并添加换行
            String cleaned = cleanedLine.toString().trim();
            if (!cleaned.isEmpty() || result.isEmpty()) {
                result.append(cleaned);
                if (!cleaned.isEmpty()) {
                    result.append('\n');
                }
            }
        }

        return result.toString().trim();
    }


    /**
     * 转换为实体对象
     */
    private PoetryQuizQuestionsConsoleCommand convertToEntity(BigInteger contentId, QuestionData questionData) {
        PoetryQuizQuestionsConsoleCommand question = new PoetryQuizQuestionsConsoleCommand();
        question.setContentId(contentId);

        // 转换题型
        QuestionType questionType;
        try {
            questionType = QuestionType.valueOf(questionData.questionType);
        } catch (IllegalArgumentException e) {
            log.warn("未知的题型: {}, 使用默认值 FILL_BLANK", questionData.questionType);
            questionType = QuestionType.FILL_BLANK;
        }
        question.setQuestionType(questionType);

        question.setQuestionStem(questionData.questionStem);

        // 转换 questionData 为 JSON 字符串
        try {
            question.setQuestionData(jacksonMapper.unwrap().writeValueAsString(questionData.questionData));
        } catch (Exception e) {
            log.error("转换 questionData 失败: {}", e.getMessage());
            question.setQuestionData("{}");
        }

        // 转换 correctAnswer 为 JSON 字符串
        try {
            question.setCorrectAnswer(jacksonMapper.unwrap().writeValueAsString(questionData.correctAnswer));
        } catch (Exception e) {
            log.error("转换 correctAnswer 失败: {}", e.getMessage());
            question.setCorrectAnswer("{}");
        }

        question.setExplanation(questionData.explanation);
        question.setDifficulty(questionData.difficulty != null ? questionData.difficulty.longValue() : 2L);

        // 转换 hints 为 JSON 字符串
        if (questionData.hints != null && !questionData.hints.isEmpty()) {
            try {
                question.setHints(jacksonMapper.unwrap().writeValueAsString(questionData.hints));
            } catch (Exception e) {
                log.error("转换 hints 失败: {}", e.getMessage());
                question.setHints("[]");
            }
        } else {
            question.setHints("[]");
        }

        question.setEnable(true);

        return question;
    }


}

