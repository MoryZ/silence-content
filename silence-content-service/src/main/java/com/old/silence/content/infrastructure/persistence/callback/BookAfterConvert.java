package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.domain.model.Book;

/**
 * @author moryzang
 */
@Component
public class BookAfterConvert implements AfterConvertCallback<Book> {

    private final MinioTemplate minioTemplate;

    public BookAfterConvert(MinioTemplate minioTemplate) {
        this.minioTemplate = minioTemplate;
    }

    @Override
    public Book onAfterConvert(Book book) {
        if (StringUtils.isNotBlank(book.getCoverImageReference())) {
            book.setCoverImageReference(minioTemplate.getInternetUrl(book.getCoverImageReference()));
        }
        if (StringUtils.isNotBlank(book.getContentReference())) {
            book.setCoverImageReference(minioTemplate.getInternetUrl(book.getContentReference()));
        }
        return book;
    }

}
