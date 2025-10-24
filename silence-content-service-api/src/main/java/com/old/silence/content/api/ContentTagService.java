package com.old.silence.content.api;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.api.dto.ContentTagCommand;
import com.old.silence.content.api.dto.ContentTagQuery;
import com.old.silence.content.api.vo.ContentTagTreeVo;
import com.old.silence.content.api.vo.ContentTagView;
import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * @author moryzang
 */
interface ContentTagService {

    @GetMapping(value = "/contentTags/{id}", params = {"type", "enabled"})
    List<ContentTagTreeVo> findTags(@PathVariable BigInteger id, @RequestParam ContentTagType type, @RequestParam Boolean enabled);

    @GetMapping(value = "/contentTags", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@SpringQueryMap ContentTagQuery contentTagQuery, Pageable pageable,
                      @ProjectedPayloadType(ContentTagView.class) Class<T> projectionType);

    @PostJsonMapping("/contentTags")
    BigInteger create(@RequestBody @Validated ContentTagCommand command);

    @PutJsonMapping(value = "/contentTags/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated ContentTagCommand command);

    @DeleteMapping("/contentTags/{id}")
    void deleteById(@PathVariable BigInteger id);
}
