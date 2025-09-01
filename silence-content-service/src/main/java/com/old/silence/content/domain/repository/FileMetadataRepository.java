package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.model.FileMetadata;

/**
 * @author MurrayZhang
 */
public interface FileMetadataRepository {

    int create(FileMetadata fileMetadata);
}
