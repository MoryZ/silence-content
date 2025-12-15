package com.old.silence.content.code.generator.spi;

import java.time.Instant;
import java.util.Optional;

/**
 * 模板资源载体：模板内容与更新时间（字符串）。
 */
public record CodeFileTemplateRecord(
        String content,
        Optional<Instant> lastModifiedDate
) {
}
