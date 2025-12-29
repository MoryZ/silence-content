package com.old.silence.content.api.vo;

import java.time.Instant;

/**
 * @author moryzang
 */
public class SearchResult {

    private String id;
    private String bookName;
    private String content;
    private String author;
    private Instant publishDate;
    private String category;
    private String highlightedBookName;
    private String highlightedContent;
    private Float score;

    // 无参构造
    public SearchResult() {
    }

    // 全参构造
    public SearchResult(String id, String bookName, String content, String author,
                        Instant publishDate, String category, Float score) {
        this.id = id;
        this.bookName = bookName;
        this.content = content;
        this.author = author;
        this.publishDate = publishDate;
        this.category = category;
        this.score = score;
    }

    // Builder 模式
    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Instant getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Instant publishDate) {
        this.publishDate = publishDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHighlightedBookName() {
        return highlightedBookName;
    }

    public void setHighlightedBookName(String highlightedBookName) {
        this.highlightedBookName = highlightedBookName;
    }

    public String getHighlightedContent() {
        return highlightedContent;
    }

    public void setHighlightedContent(String highlightedContent) {
        this.highlightedContent = highlightedContent;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public static class Builder {
        private String id;
        private String bookName;
        private String content;
        private String author;
        private Instant publishDate;
        private String category;
        private Float score;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder bookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder publishDate(Instant publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder score(Float score) {
            this.score = score;
            return this;
        }

        public SearchResult build() {
            return new SearchResult(id, bookName, content, author, publishDate, category, score);
        }
    }
}
