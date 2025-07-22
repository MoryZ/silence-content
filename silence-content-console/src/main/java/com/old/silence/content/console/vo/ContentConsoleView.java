package com.old.silence.content.console.vo;

import java.math.BigInteger;
import java.time.Instant;

import com.old.silence.content.domain.enums.ContentReferenceMode;
import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.enums.CoverImageReferenceMode;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * @author MurrayZhang
 * @Description
 */
public interface ContentConsoleView extends AuditableView {

    BigInteger getId();

    String getTitle();

    ContentType getType();

    ContentStatus getStatus();

    Instant getPublishedAt();

    String getAuthor();

    String getContentCode();

    String getCoverImageReference();

    CoverImageReferenceMode getCoverImageReferenceMode();

    ContentReferenceMode getContentReferenceMode();

    String getContentReference();
}
