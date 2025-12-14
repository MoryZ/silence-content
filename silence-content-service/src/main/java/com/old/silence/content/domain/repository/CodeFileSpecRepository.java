package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.codegen.CodeFileSpec;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * 代码文件规格仓储接口
 * 
 * DDD 领域仓储，定义代码文件规格的数据操作契约
 *
 * @author moryzang
 */
public interface CodeFileSpecRepository {
    
    /**
     * 根据主键查询
     */
    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    /**
     * 多条件分页查询
     */
    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    /**
     * 创建新规格
     */
    int create(CodeFileSpec entity);

    /**
     * 更新规格
     */
    int update(CodeFileSpec entity);

    /**
     * 删除规格
     */
    int deleteById(BigInteger id);

}