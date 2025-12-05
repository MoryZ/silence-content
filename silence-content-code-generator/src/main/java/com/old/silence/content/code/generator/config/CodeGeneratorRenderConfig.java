package com.old.silence.content.code.generator.config;

/**
 * 代码生成器渲染配置
 * 用于传递模板渲染时需要的可变参数
 *
 * @author moryzang
 */
public class CodeGeneratorRenderConfig {

    /**
     * 作者名称
     */
    private String authorName = "moryzang";

    /**
     * 应用名称（用于FeignClient的contextId等，默认：silence-content-service）
     */
    private String applicationName = "silence-content-service";

    /**
     * 主键类型（默认：BigInteger）
     */
    private String primaryType = "BigInteger";

    /**
     * 是否使用Lombok（默认：true）
     */
    private boolean useLombok = true;

    /**
     * 持久化层包名（用于import，默认：com.old.silence.data）
     */
    private String persistencePackage = "com.old.silence.data";

    public static CodeGeneratorRenderConfig defaultConfig() {
        return new CodeGeneratorRenderConfig();
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }

    public boolean isUseLombok() {
        return useLombok;
    }

    public void setUseLombok(boolean useLombok) {
        this.useLombok = useLombok;
    }

    public String getPersistencePackage() {
        return persistencePackage;
    }

    public void setPersistencePackage(String persistencePackage) {
        this.persistencePackage = persistencePackage;
    }
}
