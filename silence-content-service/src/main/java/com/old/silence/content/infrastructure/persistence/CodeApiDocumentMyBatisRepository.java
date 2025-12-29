package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.CodeApiDocument;
import com.old.silence.content.domain.repository.CodeApiDocumentRepository;
import com.old.silence.content.infrastructure.persistence.dao.CodeApiDocumentDao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
* CodeApiDocument仓储实现
*/
@Repository
public class CodeApiDocumentMyBatisRepository implements CodeApiDocumentRepository {
    private final CodeApiDocumentDao codeApiDocumentDao;

    public CodeApiDocumentMyBatisRepository(CodeApiDocumentDao codeApiDocumentDao) {
        this.codeApiDocumentDao = codeApiDocumentDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeApiDocumentDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return codeApiDocumentDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(CodeApiDocument codeApiDocument) {
        return codeApiDocumentDao.insert(codeApiDocument);
    }

    @Override
    public int bulkCreate(List<CodeApiDocument> codeApiDocuments) {
        return codeApiDocumentDao.insertAll(codeApiDocuments);
    }

    @Override
    public int bulkReplace(String tableName, List<CodeApiDocument> codeApiDocuments) {
        var rowsAffected = codeApiDocumentDao.deleteByTableName(tableName);
        return rowsAffected + codeApiDocumentDao.insertAll(codeApiDocuments);
    }

    @Override
    public int deleteById(BigInteger id) {
        return codeApiDocumentDao.deleteById(id);
    }
}