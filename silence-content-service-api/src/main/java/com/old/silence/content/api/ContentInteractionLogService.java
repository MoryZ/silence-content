package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.ContentInteractionLogCommand;
import com.old.silence.content.api.dto.ContentInteractionLogQuery;
import com.old.silence.content.api.vo.StatsVo;
import com.old.silence.content.api.vo.ContentInteractionLogView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * ContentInteractionLog服务接口
 */
interface ContentInteractionLogService {

    @GetMapping(value = "/contentInteractionLogs/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(ContentInteractionLogView.class) Class<T> projectionType);

    @GetMapping(value = "/contentInteractionLogs", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap ContentInteractionLogQuery query, Pageable pageable,
                      @ProjectedPayloadType(ContentInteractionLogView.class) Class<T> projectionType);

    @GetMapping(value = "/contentInteractionLogs/favorite/top5")
    List<StatsVo> findFavoriteTop5();

    @PostJsonMapping("/contentInteractionLogs")
    BigInteger create(@RequestBody @Validated ContentInteractionLogCommand command);

    @PutJsonMapping(value = "/contentInteractionLogs/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated ContentInteractionLogCommand command);

    @DeleteMapping("/contentInteractionLogs/{id}")
    void deleteById(@PathVariable BigInteger id);

}