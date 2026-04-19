package com.old.silence.content.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.old.silence.content.api.vo.EventGameRewardItemDomainView;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.List;

/**
 * @author EX-ZHANGMENGWEI001
 */
interface EventGameRewardItemService {

    @GetMapping("/eventGameRewardItems")
    <T> List<T> findByEventGameId(@RequestParam BigInteger eventGameId, @ProjectedPayloadType(EventGameRewardItemDomainView.class) Class<T> projectionType);

}
