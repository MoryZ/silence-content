package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.model.Book;
import com.old.silence.content.file.factory.FileStorageFactory;

/**
 * @author moryzang
 */
@Component
public class BookAfterConvert implements AfterConvertCallback<Book> {

    private final FileStorageFactory fileStorageFactory;

    public BookAfterConvert(FileStorageFactory fileStorageFactory) {
        this.fileStorageFactory = fileStorageFactory;
    }

    @Override
    public Book onAfterConvert(Book book) {
        var storageTemplate = fileStorageFactory.getStorageTemplate();
        if (StringUtils.isNotBlank(book.getCoverImageReference())) {
            book.setCoverImageReference(storageTemplate.getPreviewUrl(book.getCoverImageReference()));
        }
        if (StringUtils.isNotBlank(book.getContentReference())) {
            book.setCoverImageReference(storageTemplate.getPreviewUrl(book.getContentReference()));
        }
        return book;
    }

}
