package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.ContentReferenceMode;
import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.enums.CoverImageReferenceMode;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface ContentView {

    BigInteger getId();

    String getTitle();

    ContentStatus getStatus();

    ContentType getType();

    String getAuthor();

    String getCoverImageReference();

    CoverImageReferenceMode getCoverImageReferenceMode();

    String getContentCode();

    Instant getPublishedAt();

    ContentReferenceMode getContentReferenceMode();

    String getContentReference();

    String getKeywords();

    Boolean getStickyTop();

    Instant getStickyTopAt();

    BigInteger getParentId();

    BigInteger getRootId();

    Map<String, Object> getAttributes();

    Instant getExpiredAt();

    List<ContentContentTagView> getContentContentTags();
}
