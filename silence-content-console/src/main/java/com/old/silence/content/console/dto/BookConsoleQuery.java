package com.old.silence.content.console.dto;


import com.old.silence.content.domain.enums.BookType;

import java.math.BigInteger;

/**
* Book查询对象
*/
public class BookConsoleQuery {
    private BigInteger parentId;
    private BookType bookType;
    private String isbn;

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