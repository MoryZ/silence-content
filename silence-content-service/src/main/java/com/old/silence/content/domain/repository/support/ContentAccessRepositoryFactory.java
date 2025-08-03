package com.old.silence.content.domain.repository.support;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.model.support.ContentAccessor;
import com.old.silence.content.domain.repository.ContentAccessRepository;

/**
 * @author moryzang
 */
@Repository
public class ContentAccessRepositoryFactory {

    private final Map<ContentType, ContentAccessRepository<ContentAccessor>> repositories;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public ContentAccessRepositoryFactory(ObjectProvider<ContentAccessRepository> repositoryObjectProvider) {
        Map<ContentType, ContentAccessRepository<ContentAccessor>> typeContentAccessRepositoryEnumMap = new EnumMap<>(ContentType.class);
        repositoryObjectProvider.stream().forEach(repository -> repository.getSupportedTypes()
                .forEach(
                        contentType -> typeContentAccessRepositoryEnumMap.put((ContentType) contentType, repository)));
        repositories = typeContentAccessRepositoryEnumMap;
    }

    public ContentAccessRepository<ContentAccessor> getRepository(ContentType type) {
        return repositories.get(type);
    }
}
