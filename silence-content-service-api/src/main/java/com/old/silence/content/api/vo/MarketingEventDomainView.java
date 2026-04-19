package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;

import com.old.silence.content.domain.enums.BusinessScene;
import com.old.silence.content.domain.enums.MarketingEventStatus;
import com.old.silence.content.domain.enums.MarketingType;
import com.old.silence.content.domain.enums.ParticipantType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author yangwenchang
 */
@ProjectedPayload
public interface MarketingEventDomainView extends AuditableView {

    BigInteger getId();

    String getName();

    String getCode();

    String getDisplayName();

    String getDescription();

    Instant getStartTime();

    Instant getEndTime();

    String getCoverUrl();

    String getRuleContent();

    BigInteger getEntryRuleId();

    String getBackgroundImageIobsKey();

    MarketingEventStatus getStatus();

    List<EventGameDomainView> getEventGames();

    ParticipantType getParticipantType();

    Map<String, Object> getAttributes();

    /**
     * 活动类型
     */
    MarketingType getCategoryCode();

    BusinessScene getBusinessScene();

    /**
     * 活动所属机构
     */
    String getRegionCode();
}
