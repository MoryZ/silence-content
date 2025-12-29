package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.BookStatus;
import com.old.silence.content.domain.enums.BookType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

/**
 * Book视图接口
 */
@ProjectedPayload
public interface BookView extends AuditableView {
    BigInteger getId();

    BigInteger getParentId();

    BookType getBookType();

    String getIsbn();

    String getIsbnSeries();

    String getName();

    String getSeriesName();

    Long getVolumeNumber();

    String getVolumeName();

    String getCoverImageReference();

    String getContentReference();

    BookStatus getStatus();

    Instant getPublishedAt();

    String getAuthor();

    BigDecimal getPrice();

    String getPress();

    String getOwner();

    String getDescription();

    Long getTotalVolumes();

    Long getSortOrder();


}