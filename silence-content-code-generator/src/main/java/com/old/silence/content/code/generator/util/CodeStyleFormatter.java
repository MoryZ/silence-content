package com.old.silence.content.code.generator.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 代码风格格式化器
 * 规范import顺序、接口方法顺序、Repository方法顺序
 * 
 * @author moryzang
 */
public class CodeStyleFormatter {

    /**
     * 格式化代码，包括import排序和方法排序
     */
    public String format(String code) {
        if (code == null || code.trim().isEmpty()) {
            return code;
        }

        // 1. 排序import语句
        String formattedImports = sortImports(code);
        
        // 2. 排序接口方法（如果有@GetMapping等注解）
        String formattedMethods = sortInterfaceMethods(formattedImports);
        
        // 3. 排序Repository方法
        String formattedRepo = sortRepositoryMethods(formattedMethods);
        
        return formattedRepo;
    }

    /**
     * 排序import语句
     * 顺序：import static -> java.* -> javax.* -> jakarta.* -> org.* -> com.* -> 其他
     */
    private String sortImports(String code) {
        // 提取所有import语句
        Pattern importPattern = Pattern.compile("^import\\s+(static\\s+)?([^;]+);", Pattern.MULTILINE);
        List<String> imports = new ArrayList<>();
        Matcher matcher = importPattern.matcher(code);
        
        while (matcher.find()) {
            imports.add(matcher.group(0));
        }
        
        if (imports.isEmpty()) {
            return code;
        }
        
        // 排序import语句
        List<String> sortedImports = imports.stream()
            .sorted(new ImportComparator())
            .collect(Collectors.toList());
        
        // 替换原始import语句
        String result = code;
        for (int i = 0; i < imports.size(); i++) {
            result = result.replace(imports.get(i), sortedImports.get(i));
        }
        
        return result;
    }

    /**
     * Import比较器
     */
    private static class ImportComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            boolean aIsStatic = a.contains("import static");
            boolean bIsStatic = b.contains("import static");
            
            // static import 优先
            if (aIsStatic && !bIsStatic) return -1;
            if (!aIsStatic && bIsStatic) return 1;
            
            // 提取包名
            String aPackage = extractPackage(a);
            String bPackage = extractPackage(b);
            
            // 获取优先级
            int aPriority = getPackagePriority(aPackage);
            int bPriority = getPackagePriority(bPackage);
            
            if (aPriority != bPriority) {
                return Integer.compare(aPriority, bPriority);
            }
            
            // 同优先级按字母顺序排序
            return aPackage.compareTo(bPackage);
        }
        
        private String extractPackage(String importStatement) {
            // 移除 "import static " 或 "import "
            String cleaned = importStatement
                .replaceFirst("^import\\s+static\\s+", "")
                .replaceFirst("^import\\s+", "")
                .replace(";", "");
            return cleaned;
        }
        
        private int getPackagePriority(String packageName) {
            if (packageName.startsWith("java.")) return 1;
            if (packageName.startsWith("javax.")) return 2;
            if (packageName.startsWith("jakarta.")) return 3;
            if (packageName.startsWith("org.")) return 4;
            if (packageName.startsWith("com.")) return 5;
            return 6; // 其他包
        }
    }

    /**
     * 排序接口方法
     * 顺序：@GetMapping -> @GetJsonMapping -> @PostMapping -> @PostJsonMapping 
     *      -> @PutMapping -> @PutJsonMapping -> @DeleteMapping
     */
    private String sortInterfaceMethods(String code) {
        // 检测是否是接口或Controller类
        if (!code.contains("@RestController") && !code.contains("@Controller") 
            && !code.contains("interface ") && !code.contains("class ")) {
            return code;
        }
        
        // 提取所有方法（带注解的）
        List<MethodBlock> methods = extractMethods(code);
        
        if (methods.size() <= 1) {
            return code;
        }
        
        // 排序方法
        methods.sort(new MethodAnnotationComparator());
        
        // 重新组装代码
        return rebuildCodeWithSortedMethods(code, methods);
    }

    /**
     * 方法块（包含注解和方法体）
     */
    private static class MethodBlock {
        String annotations;
        String methodSignature;
        String methodBody;
    }

    /**
     * 提取方法
     */
    private List<MethodBlock> extractMethods(String code) {
        List<MethodBlock> methods = new ArrayList<>();
        
        // 匹配方法：注解 + 方法签名 + 方法体
        Pattern methodPattern = Pattern.compile(
            "((?:@\\w+(?:\\([^)]*\\))?\\s*)+)" + // 注解
            "\\s*" +
            "(public|private|protected)?\\s*" +
            "(?:\\w+\\s+)?" + // 返回类型
            "(\\w+)\\s*" + // 方法名
            "\\([^)]*\\)\\s*" + // 参数
            "(?:throws\\s+[^{]+)?\\s*" +
            "\\{([^{}]*(?:\\{[^{}]*\\}[^{}]*)*)\\}", // 方法体
            Pattern.MULTILINE | Pattern.DOTALL
        );
        
        Matcher matcher = methodPattern.matcher(code);
        while (matcher.find()) {
            MethodBlock block = new MethodBlock();
            block.annotations = matcher.group(1);
            block.methodSignature = matcher.group(0).substring(0, matcher.end() - matcher.group(4).length());
            block.methodBody = matcher.group(4);
            methods.add(block);
        }
        
        return methods;
    }

    /**
     * 方法注解比较器
     */
    private static class MethodAnnotationComparator implements Comparator<MethodBlock> {
        @Override
        public int compare(MethodBlock a, MethodBlock b) {
            int aPriority = getAnnotationPriority(a.annotations);
            int bPriority = getAnnotationPriority(b.annotations);
            
            if (aPriority != bPriority) {
                return Integer.compare(aPriority, bPriority);
            }
            
            // 同优先级按方法名排序
            return a.methodSignature.compareTo(b.methodSignature);
        }
        
        private int getAnnotationPriority(String annotations) {
            if (annotations.contains("@GetMapping")) return 1;
            if (annotations.contains("@GetJsonMapping")) return 2;
            if (annotations.contains("@PostMapping")) return 3;
            if (annotations.contains("@PostJsonMapping")) return 4;
            if (annotations.contains("@PutMapping")) return 5;
            if (annotations.contains("@PutJsonMapping")) return 6;
            if (annotations.contains("@DeleteMapping")) return 7;
            return 99; // 其他注解
        }
    }

    /**
     * 重新组装代码（方法已排序）
     */
    private String rebuildCodeWithSortedMethods(String originalCode, List<MethodBlock> sortedMethods) {
        // 这是一个简化的实现，实际应该更精确地替换方法位置
        // 这里先返回原代码，实际实现需要更复杂的逻辑
        return originalCode;
    }

    /**
     * 排序Repository方法
     * 顺序：find/query开头 -> create/bulkCreate -> update/bulkUpdate -> delete/bulkDelete
     */
    private String sortRepositoryMethods(String code) {
        // 检测是否是Repository接口
        if (!code.contains("Repository") && !code.contains("interface ")) {
            return code;
        }
        
        // 提取所有方法
        List<MethodBlock> methods = extractMethods(code);
        
        if (methods.size() <= 1) {
            return code;
        }
        
        // 排序Repository方法
        methods.sort(new RepositoryMethodComparator());
        
        // 重新组装代码
        return rebuildCodeWithSortedMethods(code, methods);
    }

    /**
     * Repository方法比较器
     */
    private static class RepositoryMethodComparator implements Comparator<MethodBlock> {
        @Override
        public int compare(MethodBlock a, MethodBlock b) {
            String aName = extractMethodName(a.methodSignature);
            String bName = extractMethodName(b.methodSignature);
            
            int aPriority = getMethodPriority(aName);
            int bPriority = getMethodPriority(bName);
            
            if (aPriority != bPriority) {
                return Integer.compare(aPriority, bPriority);
            }
            
            // 同优先级按方法名排序
            return aName.compareTo(bName);
        }
        
        private String extractMethodName(String methodSignature) {
            // 简单提取方法名（实际应该更精确）
            Pattern namePattern = Pattern.compile("\\s+(\\w+)\\s*\\(");
            Matcher matcher = namePattern.matcher(methodSignature);
            if (matcher.find()) {
                return matcher.group(1);
            }
            return "";
        }
        
        private int getMethodPriority(String methodName) {
            String lowerName = methodName.toLowerCase();
            if (lowerName.startsWith("find") || lowerName.startsWith("query")) return 1;
            if (lowerName.startsWith("create") || lowerName.startsWith("bulkcreate")) return 2;
            if (lowerName.startsWith("update") || lowerName.startsWith("bulkupdate")) return 3;
            if (lowerName.startsWith("delete") || lowerName.startsWith("bulkdelete")) return 4;
            return 99; // 其他方法
        }
    }
}

