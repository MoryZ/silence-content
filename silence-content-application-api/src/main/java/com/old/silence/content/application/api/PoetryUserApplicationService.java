package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.application.api.dto.PoetryUserApplicationCommand;
import com.old.silence.content.application.api.vo.PoetryUserApplicationView;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryUser服务接口
 */
interface PoetryUserApplicationService {

    @GetMapping(value = "/poetryUsers")
    <T> Optional<T> findByOpenid(@RequestParam String openid, @ProjectedPayloadType(PoetryUserApplicationView.class) Class<T> projectionType);

    @PostMapping("/poetryUsers")
    BigInteger create(@RequestBody @Validated PoetryUserApplicationCommand command);

    @PutMapping(value = "/poetryUsers/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryUserApplicationCommand command);

}