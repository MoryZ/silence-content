package com.old.silence.code.generator.util;

import org.springframework.stereotype.Component;

import com.old.silence.code.generator.strategy.CodeGenerationStrategy.CodeLayer;

/**
 * 代码格式化器
 * 对大模型生成的代码进行格式化、验证和修复
 *
 * @author moryzang
 */
@Component
public class CodeFormatter {

    /**
     * 格式化代码
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

        // 3. 基础格式化（缩进、换行等）
        String formattedCode = basicFormat(processedCode);

        return formattedCode;
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
     */
    private String basicFormat(String code) {
        // 这里可以使用Google Java Format或其他格式化工具
        // 目前先做简单的格式化处理
        return code;
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

