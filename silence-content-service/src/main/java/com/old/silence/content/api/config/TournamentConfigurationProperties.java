package com.old.silence.content.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

/**
 * @author EX-ZHANGMENGWEI001
 */
@Component
@ConfigurationProperties(prefix = "ehis.marketing.tournament")
public class TournamentConfigurationProperties {
    private List<RobotTemplate> robotTemplates;

    public List<RobotTemplate> getRobotTemplates() {
        return robotTemplates;
    }

    public void setRobotTemplates(List<RobotTemplate> robotTemplates) {
        this.robotTemplates = robotTemplates;
    }

    public static class RobotTemplate {
        private BigInteger templateId;
        private BigInteger robotId;
        private String nickname;
        private String avatarUrl;

        public BigInteger getTemplateId() {
            return templateId;
        }

        public void setTemplateId(BigInteger templateId) {
            this.templateId = templateId;
        }

        public BigInteger getRobotId() {
            return robotId;
        }

        public void setRobotId(BigInteger robotId) {
            this.robotId = robotId;
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

}
