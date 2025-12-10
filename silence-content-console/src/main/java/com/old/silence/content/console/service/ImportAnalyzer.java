package com.old.silence.content.console.service;

import com.old.silence.content.code.generator.vo.CodePreviewResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 导入分析器 - 分析生成的代码，提供导入建议
 *
 * @author moryzang
 */
@Component
public class ImportAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(ImportAnalyzer.class);

    // Java关键字和基本类型，不需要导入
    private static final Set<String> JAVA_KEYWORDS = Set.of(
            "void", "int", "long", "double", "float", "boolean", "byte", "short", "char",
            "String", "Integer", "Long", "Double", "Float", "Boolean", "Byte", "Short", "Character",
            "Object", "Class"
    );

    // 常见注解和对应的包
    private static final Map<String, String> COMMON_ANNOTATIONS = Map.ofEntries(
            Map.entry("@RestController", "org.springframework.web.bind.annotation.RestController"),
            Map.entry("@RequestMapping", "org.springframework.web.bind.annotation.RequestMapping"),
            Map.entry("@GetMapping", "org.springframework.web.bind.annotation.GetMapping"),
            Map.entry("@PostMapping", "org.springframework.web.bind.annotation.PostMapping"),
            Map.entry("@PutMapping", "org.springframework.web.bind.annotation.PutMapping"),
            Map.entry("@DeleteMapping", "org.springframework.web.bind.annotation.DeleteMapping"),
            Map.entry("@RequestBody", "org.springframework.web.bind.annotation.RequestBody"),
            Map.entry("@PathVariable", "org.springframework.web.bind.annotation.PathVariable"),
            Map.entry("@RequestParam", "org.springframework.web.bind.annotation.RequestParam"),
            Map.entry("@Service", "org.springframework.stereotype.Service"),
            Map.entry("@Component", "org.springframework.stereotype.Component"),
            Map.entry("@Autowired", "org.springframework.beans.factory.annotation.Autowired"),
            Map.entry("@Override", "java.lang.Override"),
            Map.entry("@Transactional", "org.springframework.transaction.annotation.Transactional"),
            Map.entry("@Valid", "jakarta.validation.Valid"),
            Map.entry("@NotNull", "jakarta.validation.constraints.NotNull"),
            Map.entry("@NotBlank", "jakarta.validation.constraints.NotBlank"),
            Map.entry("@Size", "jakarta.validation.constraints.Size"),
            Map.entry("@Min", "jakarta.validation.constraints.Min"),
            Map.entry("@Max", "jakarta.validation.constraints.Max")
    );

    // 常见类型和对应的包
    private static final Map<String, String> COMMON_TYPES = Map.ofEntries(
            Map.entry("List", "java.util.List"),
            Map.entry("ArrayList", "java.util.ArrayList"),
            Map.entry("Map", "java.util.Map"),
            Map.entry("HashMap", "java.util.HashMap"),
            Map.entry("Set", "java.util.Set"),
            Map.entry("HashSet", "java.util.HashSet"),
            Map.entry("Collection", "java.util.Collection"),
            Map.entry("Optional", "java.util.Optional"),
            Map.entry("Date", "java.util.Date"),
            Map.entry("LocalDate", "java.time.LocalDate"),
            Map.entry("LocalDateTime", "java.time.LocalDateTime"),
            Map.entry("LocalTime", "java.time.LocalTime"),
            Map.entry("BigDecimal", "java.math.BigDecimal"),
            Map.entry("BigInteger", "java.math.BigInteger"),
            Map.entry("ResponseEntity", "org.springframework.http.ResponseEntity"),
            Map.entry("HttpStatus", "org.springframework.http.HttpStatus")
    );

    /**
         * 分析导入结果
         */
        public record ImportAnalysisResult(Map<String, List<String>> suggestions, Map<String, List<String>> warnings) {
    }

    /**
     * 分析所有文件的导入
     */
    public ImportAnalysisResult analyzeImports(List<CodePreviewResponse.GeneratedFile> files) {
        Map<String, List<String>> suggestions = new HashMap<>();
        Map<String, List<String>> warnings = new HashMap<>();

        for (CodePreviewResponse.GeneratedFile file : files) {
            String filePath = file.getFilePath();
            String content = file.getContent();

            // 分析当前导入
            Set<String> existingImports = extractExistingImports(content);

            // 分析使用的类型和注解
            Set<String> usedAnnotations = extractUsedAnnotations(content);
            Set<String> usedTypes = extractUsedTypes(content);

            // 生成建议
            List<String> fileSuggestions = new ArrayList<>();
            List<String> fileWarnings = new ArrayList<>();

            // 检查注解导入
            for (String annotation : usedAnnotations) {
                String expectedImport = COMMON_ANNOTATIONS.get(annotation);
                if (expectedImport != null && !existingImports.contains(expectedImport)) {
                    fileSuggestions.add(expectedImport);
                    fileWarnings.add("缺失注解导入：" + annotation + " -> " + expectedImport);
                }
            }

            // 检查类型导入
            for (String type : usedTypes) {
                String expectedImport = COMMON_TYPES.get(type);
                if (expectedImport != null && !existingImports.contains(expectedImport)) {
                    fileSuggestions.add(expectedImport);
                    fileWarnings.add("缺失类型导入：" + type + " -> " + expectedImport);
                }
            }

            if (!fileSuggestions.isEmpty()) {
                suggestions.put(filePath, fileSuggestions);
            }
            if (!fileWarnings.isEmpty()) {
                warnings.put(filePath, fileWarnings);
            }
        }

        log.info("导入分析完成：{} 个文件有建议，{} 个文件有警告",
                suggestions.size(), warnings.size());

        return new ImportAnalysisResult(suggestions, warnings);
    }

    /**
     * 提取现有的导入语句
     */
    private Set<String> extractExistingImports(String content) {
        Set<String> imports = new HashSet<>();
        Pattern pattern = Pattern.compile("import\\s+([a-zA-Z0-9_.]+);");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            imports.add(matcher.group(1));
        }

        return imports;
    }

    /**
     * 提取使用的注解
     */
    private Set<String> extractUsedAnnotations(String content) {
        Set<String> annotations = new HashSet<>();
        Pattern pattern = Pattern.compile("@([A-Z][a-zA-Z0-9]*)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String annotation = "@" + matcher.group(1);
            if (COMMON_ANNOTATIONS.containsKey(annotation)) {
                annotations.add(annotation);
            }
        }

        return annotations;
    }

    /**
     * 提取使用的类型
     */
    private Set<String> extractUsedTypes(String content) {
        Set<String> types = new HashSet<>();

        // 匹配类型声明：字段、参数、返回值等
        // 例如: List<String>, Map<Long, User>, Optional<User>, ResponseEntity<Result>
        Pattern pattern = Pattern.compile("\\b([A-Z][a-zA-Z0-9]*)(?:<[^>]+>)?");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String type = matcher.group(1);
            if (COMMON_TYPES.containsKey(type) && !JAVA_KEYWORDS.contains(type)) {
                types.add(type);
            }
        }

        return types;
    }

    /**
     * 组织导入语句（按包分组排序）
     */
    public List<String> organizeImports(Set<String> imports) {
        Map<String, List<String>> grouped = imports.stream()
                .collect(Collectors.groupingBy(
                        this::getImportGroup,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<String> organized = new ArrayList<>();
        String[] order = {"import static", "java", "jakarta", "org.springframework", "com", "org", "other"};

        for (String group : order) {
            List<String> groupImports = grouped.get(group);
            if (groupImports != null && !groupImports.isEmpty()) {
                Collections.sort(groupImports);
                organized.addAll(groupImports);
                organized.add(""); // 空行分隔
            }
        }

        return organized;
    }

    /**
     * 获取导入所属的分组
     */
    private String getImportGroup(String importStatement) {
        if (importStatement.startsWith("java.")) {
            return "java";
        } else if (importStatement.startsWith("jakarta.")) {
            return "jakarta";
        } else if (importStatement.startsWith("org.springframework.")) {
            return "org.springframework";
        } else if (importStatement.startsWith("com.")) {
            return "com";
        } else if (importStatement.startsWith("org.")) {
            return "org";
        } else {
            return "other";
        }
    }
}
