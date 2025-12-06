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
    private String authorName;

    /**
     * 应用名称（用于FeignClient的contextId等，默认：silence-content-service）
     */
    private String applicationName;

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

}
