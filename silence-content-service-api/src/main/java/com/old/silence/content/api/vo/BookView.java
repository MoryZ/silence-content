package com.old.silence.content.api.vo;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.BookStatus;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface BookView extends AuditableView {

    BigInteger getId();

    String getName();

    String getAuthor();

    BookStatus getStatus();

    BigInteger getPrice();

    String getIsbn();

    String getCoverImageReference();

    Instant getPublishedAt();

    List<BookContentTagView> getBookContentTags();
}
