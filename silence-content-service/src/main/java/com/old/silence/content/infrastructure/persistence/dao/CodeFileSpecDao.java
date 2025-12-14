package com.old.silence.content.infrastructure.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.old.silence.content.domain.model.codegen.CodeFileSpec;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * 代码文件规格数据访问对象接口
 * 
 * 基于自定义 JdbcRepository 的 DAO 定义，提供代码文件规格的数据库操作方法
 *
 * @author moryzang
 */
public interface CodeFileSpecDao extends JdbcRepository<CodeFileSpec, Long> {

    /**
     * 根据模块类型获取所有启用的规格
     */
    List<CodeFileSpec> findByModuleTypeAndEnabledTrue(String moduleType);

    /**
     * 根据模板名称查询
     */
    Optional<CodeFileSpec> findByTemplateName(String templateName);

    /**
     * 根据模块类型和生成条件查询启用的规格
     */
    List<CodeFileSpec> findByModuleTypeAndGenerationConditionAndEnabledTrue(
            String moduleType, String generationCondition);

    /**
     * 查询所有启用的规格
     */
    List<CodeFileSpec> findAllEnabled();

    /**
     * 检查指定的模块类型和模板名称组合是否存在
     */
    boolean existsByModuleTypeAndTemplateName(String moduleType, String templateName);

    /**
     * 批量删除记录
     */
    int deleteByIds(List<Long> ids);
}
