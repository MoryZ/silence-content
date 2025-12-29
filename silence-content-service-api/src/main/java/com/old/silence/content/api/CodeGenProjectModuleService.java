package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.api.dto.CodeGenProjectModuleCommand;
import com.old.silence.content.api.dto.CodeGenProjectModuleQuery;
import com.old.silence.content.api.vo.CodeGenProjectModuleView;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
interface CodeGenProjectModuleService {

    @GetMapping(value = "/codeGenProjectModules", params = {"!pageNo", "!pageSize"})
    <T> List<T> findByProjectId(@RequestParam BigInteger projectId,
                                @ProjectedPayloadType(CodeGenProjectModuleView.class) Class<T> projectionType);

    @GetMapping(value = "/codeGenProjectModules", params = {"pageNo", "pageSize"})
    <T> Page<T> queryPage(@Validated @SpringQueryMap CodeGenProjectModuleQuery query, Pageable pageable,
                          @ProjectedPayloadType(CodeGenProjectModuleView.class) Class<T> projectionType);


    @PutMapping("/codeGenProjectModules")
    int bulkReplace(@RequestBody @Validated List<CodeGenProjectModuleCommand> commands);


}
