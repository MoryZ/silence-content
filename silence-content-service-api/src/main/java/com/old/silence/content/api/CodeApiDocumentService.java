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
import com.old.silence.content.api.dto.CodeApiDocumentQuery;
import com.old.silence.content.api.vo.CodeApiDocumentView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * CodeApiDocument服务接口
 */
interface CodeApiDocumentService {

    @GetMapping(value = "/codeApiDocuments/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(CodeApiDocumentView.class) Class<T> projectionType);

    @GetMapping(value = "/codeApiDocuments", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap CodeApiDocumentQuery query, Pageable pageable,
                      @ProjectedPayloadType(CodeApiDocumentView.class) Class<T> projectionType);

    @PostJsonMapping("/codeApiDocuments")
    BigInteger create(@RequestBody @Validated CodeApiDocumentCommand command);

    @PostJsonMapping("/codeApiDocuments/bulkCreate")
    void bulkCreate(@RequestBody List<CodeApiDocumentCommand> codeApiDocumentCommands);

    @PutJsonMapping(value = "/codeApiDocuments/bulkReplace")
    void bulkReplace(@RequestBody @Validated List<CodeApiDocumentCommand> commands);

    @DeleteMapping("/codeApiDocuments/{id}")
    void deleteById(@PathVariable BigInteger id);


}