package com.old.silence.content.code.generator.repository;

import com.old.silence.content.code.generator.entity.CodeFileSpecEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 代码文件规格数据访问接口
 * 
 * @author moryzang
 */
@Repository
public interface CodeFileSpecRepository extends JpaRepository<CodeFileSpecEntity, Long> {
    
    /**
     * 根据模块类型获取所有启用的规格
     */
    List<CodeFileSpecEntity> findByModuleTypeAndEnabledTrue(String moduleType);
    
    /**
     * 根据模板名称获取规格
     */
    Optional<CodeFileSpecEntity> findByTemplateName(String templateName);
    
    /**
     * 根据模块类型和生成条件获取规格
     */
    List<CodeFileSpecEntity> findByModuleTypeAndGenerationConditionAndEnabledTrue(
            String moduleType, String generationCondition);
    
    /**
     * 获取所有启用的规格
     */
    @Query("SELECT e FROM CodeFileSpecEntity e WHERE e.enabled = true ORDER BY e.moduleType, e.id")
    List<CodeFileSpecEntity> findAllEnabled();
    
    /**
     * 检查是否存在指定的模块类型和模板名称组合
     */
    boolean existsByModuleTypeAndTemplateName(String moduleType, String templateName);
}
