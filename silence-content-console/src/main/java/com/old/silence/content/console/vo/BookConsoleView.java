package com.old.silence.content.console.vo;


import com.old.silence.content.domain.enums.BookStatus;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

/**
 * @author moryzang
 */
public interface BookConsoleView extends AuditableView {

    BigInteger getId();

    String getName();

    String getAuthor();

    BookStatus getStatus();

    BigInteger getPrice();

    String getIsbn();

    String getCoverImageReference();

    Instant getPublishedAt();

    List<BookContentTagConsoleView> getBookContentTags();
}
