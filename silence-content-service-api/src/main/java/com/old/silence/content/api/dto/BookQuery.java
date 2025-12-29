package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.BookType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
 * Book查询对象
 */
public class BookQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger parentId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BookType bookType;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String isbn;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String isbnSeries;


    public BigInteger getParentId() {
        return this.parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public BookType getBookType() {
        return this.bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbnSeries() {
        return this.isbnSeries;
    }

    public void setIsbnSeries(String isbnSeries) {
        this.isbnSeries = isbnSeries;
    }

}