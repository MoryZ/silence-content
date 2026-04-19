package com.old.silence.content.api.vo;



import com.old.silence.content.domain.enums.MarketingEventStatus;
import com.old.silence.content.domain.enums.MarketingType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.Instant;

public interface MarketingEventDomainBaseView extends AuditableView {

    BigInteger getId();

    String getName();

    String getDisplayName();

    String getDescription();

    Instant getStartTime();

    Instant getEndTime();

    String getCoverUrl();

    MarketingEventStatus getStatus();

    MarketingType getCategoryCode();
}
