package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.CodeApiDocumentCommand;
import com.old.silence.content.api.dto.CodeTableMetaCommand;
import com.old.silence.content.api.dto.CodeTableMetaQuery;
import com.old.silence.content.api.vo.CodeTableMetaView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * CodeTableMeta服务接口
 */
interface CodeTableMetaService {

    @GetMapping(value = "/codeTableMetas/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(CodeTableMetaView.class) Class<T> projectionType);

    @GetMapping(value = "/codeTableMetas", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap CodeTableMetaQuery query, Pageable pageable,
                      @ProjectedPayloadType(CodeTableMetaView.class) Class<T> projectionType);

    @PostJsonMapping("/codeTableMetas")
    BigInteger create(@RequestBody @Validated CodeTableMetaCommand command);

    @PostJsonMapping("/codeTableMetas/bulkCreate")
    void bulkCreate(@RequestBody List<CodeTableMetaCommand> codeTableMetaCommands);

    @PutJsonMapping(value = "/codeTableMetas/bulkReplace")
    void bulkReplace(@RequestBody @Validated List<CodeTableMetaCommand> codeTableMetaCommands);

    @DeleteMapping("/codeTableMetas/{id}")
    void deleteById(@PathVariable BigInteger id);
}