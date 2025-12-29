package com.old.silence.content.console.api.codegen;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.CodeGenProjectModuleClient;
import com.old.silence.content.console.api.assembler.CodeGenProjectModuleCommandMapper;
import com.old.silence.content.console.api.assembler.CodeGenProjectModuleQueryMapper;
import com.old.silence.content.console.dto.CodeGenProjectModuleConsoleCommand;
import com.old.silence.content.console.dto.CodeGenProjectModuleConsoleQuery;
import com.old.silence.content.console.vo.CodeGenProjectModuleConsoleView;
import com.old.silence.core.util.CollectionUtils;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class CodeGenProjectModuleResource {

    private final CodeGenProjectModuleClient codeGenProjectModuleClient;
    private final CodeGenProjectModuleCommandMapper codeGenProjectModuleCommandMapper;
    private final CodeGenProjectModuleQueryMapper codeGenProjectModuleQueryMapper;

    public CodeGenProjectModuleResource(CodeGenProjectModuleClient codeGenProjectModuleClient,
                                        CodeGenProjectModuleCommandMapper codeGenProjectModuleCommandMapper,
                                        CodeGenProjectModuleQueryMapper codeGenProjectModuleQueryMapper) {
        this.codeGenProjectModuleClient = codeGenProjectModuleClient;
        this.codeGenProjectModuleCommandMapper = codeGenProjectModuleCommandMapper;
        this.codeGenProjectModuleQueryMapper = codeGenProjectModuleQueryMapper;
    }

    @GetMapping(value = "/codeGenProjectModules", params = {"!pageNo", "!pageSize"})
    public List<CodeGenProjectModuleConsoleView> findByProjectId(@RequestParam BigInteger projectId) {
        return codeGenProjectModuleClient.findByProjectId(projectId, CodeGenProjectModuleConsoleView.class);
    }

    @GetMapping(value = "/codeGenProjectModules", params = {"pageNo", "pageSize"})
    public Page<CodeGenProjectModuleConsoleView> queryPage(CodeGenProjectModuleConsoleQuery query, Pageable pageable) {
        var codeGenProjectModuleQuery = codeGenProjectModuleQueryMapper.convert(query);
        return codeGenProjectModuleClient.queryPage(codeGenProjectModuleQuery, pageable, CodeGenProjectModuleConsoleView.class);
    }

    @PutMapping("/codeGenProjectModules")
    public void bulkReplace(@RequestBody List<CodeGenProjectModuleConsoleCommand> commands) {
        var codeGenProjectModules = CollectionUtils.transformToList(commands, codeGenProjectModuleCommandMapper::convert);
        codeGenProjectModuleClient.bulkReplace(codeGenProjectModules);
    }

}
