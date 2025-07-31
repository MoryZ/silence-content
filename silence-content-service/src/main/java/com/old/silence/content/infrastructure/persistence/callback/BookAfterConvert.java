package com.old.silence.content.infrastructure.persistence.callback;

import io.minio.GetPresignedObjectUrlArgs;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.domain.model.Book;
import com.old.silence.content.domain.model.ContentTag;

/**
 * @author moryzang
 */
@Component
public class BookAfterConvert implements AfterConvertCallback<Book> {


    private final MinioTemplate minioTemplate;

    public BookAfterConvert(MinioTemplate minioTemplate) {
        this.minioTemplate = minioTemplate;
    }

    @NotNull
    @Override
    public Book onAfterConvert(Book book) {
        if (StringUtils.isNotBlank(book.getCoverImageReference())) {
            book.setCoverImageReference(getPresignedObjectUrl(book.getCoverImageReference()));
        }
        if (StringUtils.isNotBlank(book.getContentReference())) {
            book.setCoverImageReference(getPresignedObjectUrl(book.getContentReference()));
        }
        return book;
    }

    private String getPresignedObjectUrl(String reference) {
        var fileKey = StringUtils.substringBefore( reference, "-");
        var filename = StringUtils.substringAfter(reference, "-");
        return minioTemplate.getInternetUrl(fileKey, filename);
    }
}
