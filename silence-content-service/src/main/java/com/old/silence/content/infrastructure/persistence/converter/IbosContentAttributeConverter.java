package com.old.silence.content.infrastructure.persistence.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.data.jdbc.core.converter.JdbcAttributeConverter;
import com.old.silence.data.jdbc.core.mapping.JdbcAggregatePath;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class IbosContentAttributeConverter implements JdbcAttributeConverter<String, String> {

    private final MinioTemplate minioTemplate;

    public IbosContentAttributeConverter(MinioTemplate minioTemplate) {
        this.minioTemplate = minioTemplate;
    }

    @Override
    public String convertToDatabaseColumn(JdbcAggregatePath propertyPath, String attribute) {
        if (StringUtils.isEmpty(attribute)) {
            return attribute;
        }

        return minioTemplate.upload(UUID.randomUUID().toString(), attribute.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String convertToEntityAttribute(JdbcAggregatePath propertyPath, String iobsKey) {
        if (StringUtils.isEmpty(iobsKey)) {
            return iobsKey;
        }

        try (var input = minioTemplate.download(iobsKey, "111")) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
