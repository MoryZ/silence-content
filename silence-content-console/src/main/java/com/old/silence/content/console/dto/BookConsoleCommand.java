package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.old.silence.content.domain.enums.BookStatus;
import com.old.silence.content.domain.enums.BookType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

/**
 * Book命令对象
 */
public class BookConsoleCommand {
    private BigInteger parentId;
    private BookType bookType;
    @NotBlank
    @Size(max = 100)
    private String isbn;
    private String isbnSeries;
    @NotBlank
    @Size(max = 100)
    private String name;
    private String seriesName;
    private Long volumeNumber;
    private String volumeName;
    @NotBlank
    @Size(max = 128)
    private String coverImageReference;
    @NotBlank
    @Size(max = 128)
    private String contentReference;
    private BookStatus status;
    private Instant publishedAt;
    @NotBlank
    @Size(max = 100)
    private String author;
    @NotNull
    private BigDecimal price;
    @NotBlank
    @Size(max = 256)
    private String press;
    @NotBlank
    @Size(max = 50)
    private String owner;
    private String description;
    private Long totalVolumes;
    private Long sortOrder;

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeriesName() {
        return this.seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public Long getVolumeNumber() {
        return this.volumeNumber;
    }

    public void setVolumeNumber(Long volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public String getVolumeName() {
        return this.volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public String getCoverImageReference() {
        return this.coverImageReference;
    }

    public void setCoverImageReference(String coverImageReference) {
        this.coverImageReference = coverImageReference;
    }

    public String getContentReference() {
        return this.contentReference;
    }

    public void setContentReference(String contentReference) {
        this.contentReference = contentReference;
    }

    public BookStatus getStatus() {
        return this.status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Instant getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPress() {
        return this.press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTotalVolumes() {
        return this.totalVolumes;
    }

    public void setTotalVolumes(Long totalVolumes) {
        this.totalVolumes = totalVolumes;
    }

    public Long getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }
}