package com.old.silence.code.generator.util;

import com.old.silence.code.generator.strategy.CodeGenerationStrategy.CodeLayer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CodeFormatter单元测试
 * 
 * @author moryzang
 */
@DisplayName("代码格式化器测试")
class CodeFormatterTest {

    private static final Logger log = LoggerFactory.getLogger(CodeFormatterTest.class);
    private final CodeFormatter formatter = new CodeFormatter();

    @Test
    @DisplayName("测试基础缩进处理")
    void testBasicIndentation() {
        String code = "public class Test{\n" +
                "public void method(){\n" +
                "if(condition){\n" +
                "doSomething();\n" +
                "}\n" +
                "}\n" +
                "}";
        
        String formatted = formatter.format(code, CodeLayer.SERVICE);
        
        log.info("原始代码:\n{}", code);
        log.info("格式化后:\n{}", formatted);
        
        assertTrue(formatted.contains("    public void method()"), "应该包含正确的缩进");
    }

    @Test
    @DisplayName("测试大括号处理")
    void testBraceHandling() {
        String code = "public class Test{\n" +
                "void method1(){\n" +
                "}\n" +
                "void method2(){\n" +
                "if(true){\n" +
                "doSomething();\n" +
                "}\n" +
                "}\n" +
                "}";
        
        String formatted = formatter.format(code, CodeLayer.SERVICE);
        
        log.info("原始代码:\n{}", code);
        log.info("格式化后:\n{}", formatted);
        
        assertTrue(formatted.contains("    void method1() {"), "方法应该有正确的缩进");
        assertTrue(formatted.contains("        if (true) {"), "嵌套代码块应该有正确的缩进");
    }

    @Test
    @DisplayName("测试空行处理")
    void testEmptyLineHandling() {
        String code = "public class Test{\n" +
                "\n\n\n" +
                "void method1(){\n" +
                "}\n" +
                "\n\n\n" +
                "void method2(){\n" +
                "}\n" +
                "}";
        
        String formatted = formatter.format(code, CodeLayer.SERVICE);
        
        log.info("原始代码（空行数）: {}", countLines(code));
        log.info("格式化后（空行数）: {}", countLines(formatted));
        log.info("格式化后:\n{}", formatted);
        
        assertFalse(formatted.contains("\n\n\n"), "多余的空行应该被清理");
    }

    @Test
    @DisplayName("测试Markdown代码块清理")
    void testMarkdownCleaning() {
        String code = "```java\n" +
                "public class Test {\n" +
                "    void method() {\n" +
                "    }\n" +
                "}\n" +
                "```";
        
        String formatted = formatter.format(code, CodeLayer.SERVICE);
        
        log.info("原始代码:\n{}", code);
        log.info("格式化后:\n{}", formatted);
        
        assertFalse(formatted.contains("```java"), "Markdown标记应该被移除");
        assertFalse(formatted.contains("```"), "Markdown标记应该被移除");
    }

    @Test
    @DisplayName("测试多行注释处理")
    void testMultiLineComment() {
        String code = "public class Test {\n" +
                "    /* 这是注释\n" +
                "     * 多行注释\n" +
                "     */\n" +
                "    void method() {\n" +
                "        // 单行注释\n" +
                "    }\n" +
                "}";
        
        String formatted = formatter.format(code, CodeLayer.SERVICE);
        
        log.info("原始代码:\n{}", code);
        log.info("格式化后:\n{}", formatted);
        
        assertTrue(formatted.contains("/*"), "注释应该被保留");
        assertTrue(formatted.contains("*/"), "注释应该被保留");
    }

    @Test
    @DisplayName("测试复杂代码格式化")
    void testComplexCode() {
        String code = "package com.test;\n" +
                "import java.util.List;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "public class TestService{\n" +
                "private List<String> items;\n" +
                "public void process(){\n" +
                "for(String item:items){\n" +
                "if(item!=null){\n" +
                "System.out.println(item);\n" +
                "}\n" +
                "}\n" +
                "}\n" +
                "}";
        
        String formatted = formatter.format(code, CodeLayer.SERVICE);
        
        log.info("原始代码:\n{}", code);
        log.info("格式化后:\n{}", formatted);
        
        assertNotNull(formatted, "格式化结果不应为空");
        assertTrue(formatted.contains("public class TestService"), "类定义应该保留");
    }

    @Test
    @DisplayName("测试代码验证")
    void testValidate() {
        String validCode = "public class Test { }";
        String invalidCode = "not a class";
        
        assertTrue(formatter.validate(validCode), "有效代码应该通过验证");
        assertFalse(formatter.validate(invalidCode), "无效代码应该不通过验证");
    }

    /**
     * 统计代码行数
     */
    private int countLines(String code) {
        if (code == null || code.isEmpty()) {
            return 0;
        }
        return code.split("\n").length;
    }
}

