package com.old.silence.content.code.generator.util;

import org.springframework.stereotype.Component;

import com.old.silence.content.code.generator.strategy.CodeGenerationStrategy.CodeLayer;

/**
 * 代码格式化器
 * 对大模型生成的代码进行格式化、验证和修复
 *
 * @author moryzang
 */
@Component
public class CodeFormatter {

    private final CodeStyleFormatter styleFormatter;

    public CodeFormatter() {
        this.styleFormatter = new CodeStyleFormatter();
    }

    /**
     * 格式化代码
     * 包括：清理代码、排序import、排序方法、格式化
     *
     * @param code  原始代码
     * @param layer 代码层级
     * @return 格式化后的代码
     */
    public String format(String code, CodeLayer layer) {
        // 1. 清理代码（移除markdown代码块标记等）
        String cleanedCode = cleanCode(code);

        // 2. 根据层级进行特定处理
        String processedCode = processByLayer(cleanedCode, layer);

        // 3. 代码风格格式化（排序import、排序方法等）
        String styleFormatted = styleFormatter.format(processedCode);

        // 4. 基础格式化（缩进、换行等）
        return basicFormat(styleFormatted);
    }

    /**
     * 清理代码
     * 移除markdown代码块标记、多余的空行等
     */
    private String cleanCode(String code) {
        // 移除 ```java 或 ``` 标记
        String cleaned = code.replaceAll("```java\\s*", "")
                .replaceAll("```\\s*", "")
                .trim();

        // 移除开头的说明性文字（如果存在）
        if (cleaned.startsWith("以下是") || cleaned.startsWith("根据")) {
            int codeStart = cleaned.indexOf("public class");
            if (codeStart > 0) {
                cleaned = cleaned.substring(codeStart);
            }
        }

        return cleaned;
    }

    /**
     * 根据层级处理代码
     */
    private String processByLayer(String code, CodeLayer layer) {
        // 可以根据不同层级进行特定处理
        // 例如：验证包名、导入语句等
        return code;
    }

    /**
     * 基础格式化
     * 处理缩进、换行、大括号位置等
     */
    private String basicFormat(String code) {
        if (code == null || code.trim().isEmpty()) {
            return code;
        }

        // 先统一换行符
        code = code.replace("\r\n", "\n").replace("\r", "\n");
        
        StringBuilder formatted = new StringBuilder();
        String[] lines = code.split("\n");
        int indentLevel = 0;
        final int INDENT_SIZE = 4; // 使用4个空格作为缩进

        boolean inMultiLineComment = false;
        boolean previousLineWasEmpty = false;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String trimmed = line.trim();
            
            // 处理多行注释
            if (trimmed.startsWith("/*") && !trimmed.contains("*/")) {
                inMultiLineComment = true;
            }
            if (trimmed.contains("*/")) {
                inMultiLineComment = false;
            }
            
            // 跳过空行（但保留必要的空行）
            if (trimmed.isEmpty()) {
                // 如果上一行也是空行，跳过（避免连续多个空行）
                if (!previousLineWasEmpty && i > 0) {
                    formatted.append("\n");
                    previousLineWasEmpty = true;
                }
                continue;
            }
            previousLineWasEmpty = false;

            // 处理右大括号，减少缩进（在添加缩进之前）
            if (trimmed.startsWith("}") && !inMultiLineComment) {
                indentLevel = Math.max(0, indentLevel - 1);
            }

            // 添加缩进
            String indent = " ".repeat(indentLevel * INDENT_SIZE);
            formatted.append(indent).append(trimmed);

            // 处理左大括号，增加缩进（如果大括号在行尾）
            if (!inMultiLineComment) {
                // 计算大括号数量
                int openBraces = countChar(trimmed, '{');
                int closeBraces = countChar(trimmed, '}');
                
                // 如果这行有右大括号，已经在上面的逻辑中处理了
                // 现在处理左大括号
                if (trimmed.endsWith("{") && !trimmed.startsWith("//")) {
                    indentLevel++;
                } else if (trimmed.equals("{")) {
                    indentLevel++;
                } else if (openBraces > closeBraces) {
                    // 如果左大括号多于右大括号，增加缩进
                    indentLevel += (openBraces - closeBraces);
                }
            }

            formatted.append("\n");

            // 处理超长行（简单换行，在合适的位置）
            // 这里可以添加更复杂的换行逻辑，比如在逗号、运算符后换行
        }

        // 清理多余的空行（连续超过2个空行只保留1个）
        String result = formatted.toString();
        result = result.replaceAll("\n{3,}", "\n\n");
        
        // 确保文件末尾只有一个换行
        result = result.replaceAll("\n+$", "\n");

        return result;
    }
    
    /**
     * 统计字符出现次数
     */
    private int countChar(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }

    /**
     * 验证代码
     *
     * @param code 代码
     * @return 是否有效
     */
    public boolean validate(String code) {
        // 基础验证：检查是否包含类定义、方法等
        return code.contains("class") || code.contains("interface");
    }
}

