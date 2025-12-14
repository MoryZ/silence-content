package com.old.silence.content.code.generator.spi;

/**
 * 模板资源载体：模板内容与更新时间（字符串）。
 */
public record CodeFileTemplateRecord(
        String content,
        String updatedDate
) {
}
