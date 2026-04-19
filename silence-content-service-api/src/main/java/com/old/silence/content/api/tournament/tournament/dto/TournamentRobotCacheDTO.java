package com.old.silence.content.api.tournament.tournament.dto;

/**
 * @author moryzang
 */
public class TournamentRobotCacheDTO {
    private String nickname;
    private String avatarUrl;

    // 构造方法
    public TournamentRobotCacheDTO() {}

    public TournamentRobotCacheDTO(String nickname, String avatarUrl) {
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
