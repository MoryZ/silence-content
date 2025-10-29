package com.old.silence.content.api.assembler.support;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.domain.model.support.ContentAccessor;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author moryzang
 */
@Component
public class ContentMapperFactory {

    private final Map<Class<? extends ContentCommand>, ContentAccessMapper<ContentCommand, ContentAccessor>> mappers;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public ContentMapperFactory(ObjectProvider<ContentAccessMapper> mapperProvider
    ) {
        this.mappers = mapperProvider.stream().collect(Collectors.toMap(ContentMapperFactory::resolveCommandType,
                mapper -> (ContentAccessMapper<ContentCommand, ContentAccessor>) mapper));
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends ContentCommand> resolveCommandType(ContentAccessMapper<ContentCommand, ContentAccessor> mapper) {
        var typeArguments = GenericTypeResolver.resolveTypeArguments(mapper.getClass(), ContentAccessMapper.class);
        Objects.requireNonNull(typeArguments);
        return (Class<? extends ContentCommand>) typeArguments[0];
    }

    public ContentAccessMapper<ContentCommand, ContentAccessor> getMapper(ContentCommand command) {
        return mappers.get(command.getClass());
    }

}
