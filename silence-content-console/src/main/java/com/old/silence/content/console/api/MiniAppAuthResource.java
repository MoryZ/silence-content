package com.old.silence.content.console.api;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.console.dto.LoginRequest;
import com.old.silence.content.console.service.MiniAppAuthService;
import com.old.silence.content.console.vo.LoginResponse;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
@Validated
public class MiniAppAuthResource {


    private static final Logger log = LoggerFactory.getLogger(MiniAppAuthResource.class);
    private final MiniAppAuthService miniAppAuthService;

    public MiniAppAuthResource(MiniAppAuthService miniAppAuthService) {
        this.miniAppAuthService = miniAppAuthService;
    }

    /**
     * 小程序登录接口
     */
    @PostMapping("/auth/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        log.info("小程序登录请求: {}", request.getCode());
        return miniAppAuthService.login(request);
    }

    /*    *//**
     * 刷新token
     *//*
    @PostMapping("/refresh-token")
    public LoginResponse refreshToken(@RequestHeader("Authorization") String token) {
        // 刷新token逻辑
        return miniAppAuthService.refreshToken(token);
    }

    *//**
     * 绑定手机号
     *//*
    @PostMapping("/bind-phone")
    public void bindPhone(@Valid @RequestBody BindPhoneRequest request) {
        miniAppAuthService.bindPhone(request);
    }*/
}