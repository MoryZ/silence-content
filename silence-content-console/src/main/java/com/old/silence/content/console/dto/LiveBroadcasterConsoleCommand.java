package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import com.old.silence.content.domain.enums.LiveBroadcasterRoleType;
import com.old.silence.content.domain.enums.LiveChannel;
import com.old.silence.content.domain.enums.LivePlatform;

/**
* LiveBroadcaster命令对象
*/
public class LiveBroadcasterConsoleCommand {
    @NotNull
    private LivePlatform platform;
    @NotNull
    private LiveChannel channel;
    @NotBlank
    @Size(max = 64)
    private String channelAppid;
    @NotNull
    private LiveBroadcasterRoleType roleType;
    @NotBlank
    @Size(max = 300)
    private String umAccount;
    @NotBlank
    @Size(max = 50)
    private String nickname;
    @NotBlank
    @Size(max = 20)
    private String wechatId;

    public LivePlatform getPlatform() {
        return platform;
    }

    public void setPlatform(LivePlatform platform) {
        this.platform = platform;
    }

    public LiveChannel getChannel() {
        return channel;
    }

    public void setChannel(LiveChannel channel) {
        this.channel = channel;
    }

    public String getChannelAppid() {
        return this.channelAppid;
    }

    public void setChannelAppid(String channelAppid) {
        this.channelAppid = channelAppid;
    }

    public LiveBroadcasterRoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(LiveBroadcasterRoleType roleType) {
        this.roleType = roleType;
    }

    public String getUmAccount() {
        return this.umAccount;
    }

    public void setUmAccount(String umAccount) {
        this.umAccount = umAccount;
    }
    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getWechatId() {
        return this.wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

}