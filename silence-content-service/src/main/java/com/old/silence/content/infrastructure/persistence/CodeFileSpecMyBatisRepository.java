package com.old.silence.content.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.codegen.CodeFileSpec;
import com.old.silence.content.domain.repository.CodeFileSpecRepository;
import com.old.silence.content.infrastructure.persistence.dao.CodeFileSpecDao;

/**
 * 代码文件规格仓储实现类
 * 
 * 基于自定义 JDBC 框架的 DAO 层实现，真实的数据库操作由 {@link CodeFileSpecDao} 负责
 *
 * @author moryzang
 */
@Repository
public class CodeFileSpecMyBatisRepository implements CodeFileSpecRepository {

    private final CodeFileSpecDao codeFileSpecDao;

    public CodeFileSpecMyBatisRepository(CodeFileSpecDao codeFileSpecDao) {
        this.codeFileSpecDao = codeFileSpecDao;
    }

    @Override
    public <T> Optional<T> findById(Long id, Class<T> projectionType) {
        return codeFileSpecDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return codeFileSpecDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public List<CodeFileSpec> findByModuleTypeAndEnabledTrue(String moduleType) {
        return codeFileSpecDao.findByModuleTypeAndEnabledTrue(moduleType);
    }

    @Override
    public Optional<CodeFileSpec> findByTemplateName(String templateName) {
        return codeFileSpecDao.findByTemplateName(templateName);
    }

    @Override
    public List<CodeFileSpec> findByModuleTypeAndGenerationConditionAndEnabledTrue(
            String moduleType, String generationCondition) {
        return codeFileSpecDao.findByModuleTypeAndGenerationConditionAndEnabledTrue(moduleType, generationCondition);
    }

    @Override
    public List<CodeFileSpec> findAllEnabled() {
        return codeFileSpecDao.findAllEnabled();
    }

    @Override
    public boolean existsByModuleTypeAndTemplateName(String moduleType, String templateName) {
        return codeFileSpecDao.existsByModuleTypeAndTemplateName(moduleType, templateName);
    }

    @Override
    public int create(CodeFileSpec entity) {
        return codeFileSpecDao.insert(entity);
    }

    @Override
    public int update(CodeFileSpec entity) {
        return codeFileSpecDao.update(entity);
    }

    @Override
    public int deleteById(Long id) {
        return codeFileSpecDao.deleteById(id);
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        return codeFileSpecDao.deleteByIds(ids);
    }
}
