package com.old.silence.content.console.dto;


import com.old.silence.content.domain.enums.LiveBroadcasterRoleType;
import com.old.silence.content.domain.enums.LiveChannel;
import com.old.silence.content.domain.enums.LivePlatform;

/**
* LiveBroadcaster查询对象
*/
public class LiveBroadcasterConsoleQuery {
    private LivePlatform platform;
    private LiveChannel channel;
    private String channelAppid;

    private LiveBroadcasterRoleType roleType;
    private String umAccount;

    private String nickname;

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
        return channelAppid;
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
        return umAccount;
    }

    public void setUmAccount(String umAccount) {
        this.umAccount = umAccount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }
}