package com.old.silence.content.console.service;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.PoetryUserClient;
import com.old.silence.content.api.PoetryUserLoginLogClient;
import com.old.silence.content.api.dto.PoetryUserCommand;
import com.old.silence.content.api.dto.PoetryUserLoginLogCommand;
import com.old.silence.content.console.dto.LoginRequest;
import com.old.silence.content.console.vo.LoginResponse;
import com.old.silence.content.console.vo.PoetryUserConsoleView;
import com.old.silence.content.console.vo.UserResponse;
import com.old.silence.content.console.vo.WxMaLoginResult;
import com.old.silence.content.domain.enums.LoginType;
import com.old.silence.core.exception.ResourceNotFoundException;

import java.time.Instant;
import java.util.Optional;

/**
 * @author moryzang
 */
@Service
public class MiniAppAuthService {


    private static final Logger log = LoggerFactory.getLogger(MiniAppAuthService.class);
    private final WxMaLoginService wxMaLoginService;
    private final PoetryUserClient poetryUserClient;
    private final PoetryUserLoginLogClient poetryUserLoginLogClient;


    public MiniAppAuthService(WxMaLoginService wxMaLoginService,
                              PoetryUserClient poetryUserClient,
                              PoetryUserLoginLogClient poetryUserLoginLogClient) {
        this.wxMaLoginService = wxMaLoginService;
        this.poetryUserClient = poetryUserClient;
        this.poetryUserLoginLogClient = poetryUserLoginLogClient;
    }

    /**
     * 小程序登录并返回业务token
     */
    public LoginResponse login(LoginRequest request) {
        try {
            // 1. 根据code获取微信session信息
            WxMaLoginResult wxLoginResult = wxMaLoginService.loginByCode(request.getCode());

            // 2. 校验用户信息完整性（如果需要）
            if (StringUtils.isNotBlank(request.getRawData()) &&
                    StringUtils.isNotBlank(request.getSignature())) {
                boolean checkResult = wxMaLoginService.checkUserInfo(
                        wxLoginResult.getSessionKey(),
                        request.getRawData(),
                        request.getSignature()
                );
                if (!checkResult) {
                    throw new RuntimeException("用户信息校验失败");
                }
            }

            // 3. 解密用户信息（如果需要）
            WxMaUserInfo userInfo = null;
            if (StringUtils.isNotBlank(request.getRawData()) &&
                    StringUtils.isNotBlank(request.getIv())) {
                userInfo = wxMaLoginService.decryptUserInfo(
                        wxLoginResult.getSessionKey(),
                        request.getRawData(),
                        request.getIv()
                );
            }

            // 4. 解密手机号（如果需要）
            WxMaPhoneNumberInfo phoneInfo = null;
            if (StringUtils.isNotBlank(request.getPhoneEncryptedData()) &&
                    StringUtils.isNotBlank(request.getPhoneIv())) {
                phoneInfo = wxMaLoginService.decryptPhoneNumber(
                        wxLoginResult.getSessionKey(),
                        request.getPhoneEncryptedData(),
                        request.getPhoneIv()
                );
            }

            // 5. 处理业务登录逻辑
            return processBusinessLogin(wxLoginResult, userInfo, phoneInfo);

        } catch (WxErrorException e) {
            log.error("微信接口调用失败", e);
            throw new RuntimeException("登录失败: " + e.getError().getErrorMsg());
        } catch (Exception e) {
            log.error("小程序登录异常", e);
            throw new RuntimeException("登录失败");
        }
    }

    /**
     * 处理业务登录逻辑
     */
    private LoginResponse processBusinessLogin(WxMaLoginResult wxLoginResult,
                                               WxMaUserInfo userInfo,
                                               WxMaPhoneNumberInfo phoneInfo) {
        String openid = wxLoginResult.getOpenid();
        String unionid = wxLoginResult.getUnionid();

        // 查询或创建用户
        Optional<PoetryUserConsoleView> userOptional = poetryUserClient.findByOpenid(openid, PoetryUserConsoleView.class);

        if (userOptional.isEmpty()) {
            userOptional = Optional.of(createNewUser(openid, unionid, userInfo, phoneInfo));
        } else {
            var user = userOptional.get();

            // 更新用户信息
            updateUserInfo(user, userInfo, phoneInfo);
        }

        var user = userOptional.get();
        // 生成业务token
        String token = generateToken(user);

        // 记录登录日志
        PoetryUserLoginLogCommand poetryUserLoginLogCommand = new PoetryUserLoginLogCommand();
        poetryUserLoginLogCommand.setOpenid(openid);
        poetryUserLoginLogCommand.setIpAddress(openid);
        poetryUserLoginLogCommand.setLoginType(LoginType.MINI_PROGRAM);
        poetryUserLoginLogCommand.setLoginTime(Instant.now());
        poetryUserLoginLogCommand.setSessionKey(token);
        poetryUserLoginLogCommand.setIpAddress("");
        poetryUserLoginLogCommand.setLoginStatus(true);
        poetryUserLoginLogCommand.setUserId(user.getId());

        poetryUserLoginLogClient.create(poetryUserLoginLogCommand);

        var loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setUserInfo(buildUserResponse(user));
        loginResponse.setOpenid(openid);
        loginResponse.setSessionKey(wxLoginResult.getSessionKey());
        return loginResponse;
    }

    /**
     * 创建新用户
     */
    private PoetryUserConsoleView createNewUser(String openid, String unionid,
                                                WxMaUserInfo userInfo, WxMaPhoneNumberInfo phoneInfo) {
        PoetryUserCommand user = new PoetryUserCommand();
        user.setOpenid(openid);
        user.setUnionid(unionid);

        if (userInfo != null) {
            user.setNickname(userInfo.getNickName());
            user.setAvatarUrl(userInfo.getAvatarUrl());
        }

        if (phoneInfo != null) {
            user.setPhone(phoneInfo.getPhoneNumber());
        }

        var userId = poetryUserClient.create(user);
        return poetryUserClient.findById(userId, PoetryUserConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo(PoetryUserConsoleView user, WxMaUserInfo userInfo,
                                WxMaPhoneNumberInfo phoneInfo) {
        boolean updated = false;
        PoetryUserCommand poetryUserCommand = new PoetryUserCommand();
        if (userInfo != null) {
            poetryUserCommand.setNickname(userInfo.getNickName());
            poetryUserCommand.setAvatarUrl(userInfo.getAvatarUrl());
            updated = true;
        }

        if (phoneInfo != null && StringUtils.isBlank(user.getPhone())) {
            poetryUserCommand.setPhone(phoneInfo.getPhoneNumber());
            updated = true;
        }

        if (updated) {
            poetryUserClient.update(user.getId(), poetryUserCommand);
        }
    }

    /**
     * 生成业务token
     */
    private String generateToken(PoetryUserConsoleView user) {
        // 使用JWT或其他方式生成token
        return String.join("-", String.valueOf(user.getId()), user.getOpenid());
    }

    /**
     * 构建用户响应信息
     */
    private UserResponse buildUserResponse(PoetryUserConsoleView user) {
        var userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setNickname(user.getNickname());
        userResponse.setAvatar(user.getAvatarUrl());
        userResponse.setPhone(user.getPhone());
        return userResponse;
    }
}
