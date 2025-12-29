package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.UserInteractionLogCommand;
import com.old.silence.content.api.dto.UserInteractionLogQuery;
import com.old.silence.content.api.vo.StatsVo;
import com.old.silence.content.api.vo.UserInteractionLogView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * UserInteractionLog服务接口
 */
interface UserInteractionLogService {

    @GetMapping(value = "/userInteractionLogs/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(UserInteractionLogView.class) Class<T> projectionType);

    @GetMapping(value = "/userInteractionLogs", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap UserInteractionLogQuery query, Pageable pageable,
                      @ProjectedPayloadType(UserInteractionLogView.class) Class<T> projectionType);

    @GetMapping(value = "/userInteractionLogs/favorite/top5")
    List<StatsVo> findFavoriteTop5();

    @PostJsonMapping("/userInteractionLogs")
    BigInteger create(@RequestBody @Validated UserInteractionLogCommand command);

    @PutJsonMapping(value = "/userInteractionLogs/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated UserInteractionLogCommand command);

    @DeleteMapping("/userInteractionLogs/{id}")
    void deleteById(@PathVariable BigInteger id);

}