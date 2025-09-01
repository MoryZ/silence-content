package com.old.silence.content.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.FileMetadata;
import com.old.silence.content.domain.repository.FileMetadataRepository;
import com.old.silence.content.infrastructure.persistence.dao.FileMetadataDao;

/**
 * @author MurrayZhang
 */
@Repository
public class FileMetadataMyBatisRepository implements FileMetadataRepository {

    private final FileMetadataDao fileMetadataDao;

    public FileMetadataMyBatisRepository(FileMetadataDao fileMetadataDao) {
        this.fileMetadataDao = fileMetadataDao;
    }

    @Override
    public int create(FileMetadata fileMetadata) {
        return fileMetadataDao.insert(fileMetadata);
    }
}
