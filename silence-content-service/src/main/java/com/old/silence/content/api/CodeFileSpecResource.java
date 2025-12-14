package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.CodeFileSpecMapper;
import com.old.silence.content.api.dto.CodeFileSpecCommand;
import com.old.silence.content.api.dto.CodeFileSpecQuery;
import com.old.silence.content.api.dto.CodeFileSpecView;
import com.old.silence.content.domain.model.codegen.CodeFileSpec;
import com.old.silence.content.domain.repository.CodeFileSpecRepository;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;
import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * 代码文件规格管理 REST API
 * 
 * 提供代码文件规格的 CRUD 和查询接口
 * 支持动态配置文件规格而无需重新编译
 *
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class CodeFileSpecResource {

    private final CodeFileSpecRepository codeFileSpecRepository;
    private final CodeFileSpecMapper codeFileSpecMapper;

    public CodeFileSpecResource(CodeFileSpecRepository codeFileSpecRepository,
                               CodeFileSpecMapper codeFileSpecMapper) {
        this.codeFileSpecRepository = codeFileSpecRepository;
        this.codeFileSpecMapper = codeFileSpecMapper;
    }

    /**
     * 根据主键获取规格详情
     */
    @GetMapping("/codeFileSpecs/{id}")
    public CodeFileSpecView findById(@PathVariable Long id) {
        return codeFileSpecRepository.findById(id, CodeFileSpecView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * 分页查询规格列表
     */
    @GetMapping(value = "/codeFileSpecs", params = {"pageNo", "pageSize"})
    public Page<CodeFileSpecView> queryPage(CodeFileSpecQuery query, Pageable pageable) {
        var criteria = QueryCriteriaConverter.convert(query, CodeFileSpec.class);
        return codeFileSpecRepository.findByCriteria(criteria, pageable, CodeFileSpecView.class);
    }

    /**
     * 创建新规格
     */
    @PostMapping("/codeFileSpecs")
    public Long create(@RequestBody CodeFileSpecCommand command) {
        var entity = codeFileSpecMapper.convert(command);
        codeFileSpecRepository.create(entity);
        return entity.getId();
    }

    /**
     * 更新规格
     */
    @PutMapping("/codeFileSpecs/{id}")
    public void update(@PathVariable Long id, @RequestBody CodeFileSpecCommand command) {
        var entity = codeFileSpecMapper.convert(command);
        entity.setId(id);
        validateModifyingResult(codeFileSpecRepository.update(entity));
    }

    /**
     * 删除规格
     */
    @DeleteMapping("/codeFileSpecs/{id}")
    public void deleteById(@PathVariable Long id) {
        validateModifyingResult(codeFileSpecRepository.deleteById(id));
    }
}
