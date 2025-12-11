package com.old.silence.content.code.generator.spi;

/**
 * 提供模板内容的抽象仓库接口，由上层应用实现（如 console 模块），
 * 代码生成库仅依赖该接口以避免直接依赖具体数据访问技术。
 */
public interface TemplatesRepository {
    /**
     * 根据模板名加载模板资源。
     * @param name 模板名称（例如 ftl 文件名）
     * @return 模板资源（包含内容与更新时间），不存在时返回 null
     */
    TemplateResource load(String name);
}
