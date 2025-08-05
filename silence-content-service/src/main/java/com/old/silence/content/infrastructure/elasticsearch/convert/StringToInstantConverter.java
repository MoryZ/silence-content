package com.old.silence.content.infrastructure.elasticsearch.convert;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

/**
 * @author moryzang
 */
public class StringToInstantConverter implements Converter<String, Instant> {
    @Override
    public Instant convert(@NotNull String source) {
        return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(source));
    }
}