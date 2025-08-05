package com.old.silence.content.api.dto;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

/**
 * @author moryzang
 */
public class ContentIndexQuery {

    private String title;

    private String contentCode;

    private String author;

    private String lastModifiedBy;

    private String auditCode;

    private String createdBy;

    private Byte type;

    private List<Byte> contentTypes;

    private Byte status;

    private Instant publishedAtStart;

    private Instant publishedAtEnd;

    private Instant expiredAtStart;

    private Instant expiredAtEnd;

    private Instant stickyTopExpiredAtStart;

    private Instant stickyTopExpiredAtEnd;

    private BigInteger parentId;

    private List<Integer> businessStatuses;

    private Byte contentReferenceMode;

    private List<BigInteger> tenantIds;

    private Boolean disclosure;

    private String productCode;

    private Instant onSaleAtStart;

    private Instant onSaleAtEnd;

    private Boolean leaf;

    private BigInteger rootId;

    private List<String> tagCodes;

    private List<BigInteger> tagIds;

    private String tagRelation;

    private String globalRelation;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getAuditCode() {
        return auditCode;
    }

    public void setAuditCode(String auditCode) {
        this.auditCode = auditCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public List<Byte> getContentTypes() {
        return contentTypes;
    }

    public void setContentTypes(List<Byte> contentTypes) {
        this.contentTypes = contentTypes;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Instant getPublishedAtStart() {
        return publishedAtStart;
    }

    public void setPublishedAtStart(Instant publishedAtStart) {
        this.publishedAtStart = publishedAtStart;
    }

    public Instant getPublishedAtEnd() {
        return publishedAtEnd;
    }

    public void setPublishedAtEnd(Instant publishedAtEnd) {
        this.publishedAtEnd = publishedAtEnd;
    }

    public Instant getExpiredAtStart() {
        return expiredAtStart;
    }

    public void setExpiredAtStart(Instant expiredAtStart) {
        this.expiredAtStart = expiredAtStart;
    }

    public Instant getExpiredAtEnd() {
        return expiredAtEnd;
    }

    public void setExpiredAtEnd(Instant expiredAtEnd) {
        this.expiredAtEnd = expiredAtEnd;
    }

    public Instant getStickyTopExpiredAtStart() {
        return stickyTopExpiredAtStart;
    }

    public void setStickyTopExpiredAtStart(Instant stickyTopExpiredAtStart) {
        this.stickyTopExpiredAtStart = stickyTopExpiredAtStart;
    }

    public Instant getStickyTopExpiredAtEnd() {
        return stickyTopExpiredAtEnd;
    }

    public void setStickyTopExpiredAtEnd(Instant stickyTopExpiredAtEnd) {
        this.stickyTopExpiredAtEnd = stickyTopExpiredAtEnd;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public List<Integer> getBusinessStatuses() {
        return businessStatuses;
    }

    public void setBusinessStatuses(List<Integer> businessStatuses) {
        this.businessStatuses = businessStatuses;
    }

    public Byte getContentReferenceMode() {
        return contentReferenceMode;
    }

    public void setContentReferenceMode(Byte contentReferenceMode) {
        this.contentReferenceMode = contentReferenceMode;
    }

    public List<BigInteger> getTenantIds() {
        return tenantIds;
    }

    public void setTenantIds(List<BigInteger> tenantIds) {
        this.tenantIds = tenantIds;
    }

    public Boolean getDisclosure() {
        return disclosure;
    }

    public void setDisclosure(Boolean disclosure) {
        this.disclosure = disclosure;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Instant getOnSaleAtStart() {
        return onSaleAtStart;
    }

    public void setOnSaleAtStart(Instant onSaleAtStart) {
        this.onSaleAtStart = onSaleAtStart;
    }

    public Instant getOnSaleAtEnd() {
        return onSaleAtEnd;
    }

    public void setOnSaleAtEnd(Instant onSaleAtEnd) {
        this.onSaleAtEnd = onSaleAtEnd;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public BigInteger getRootId() {
        return rootId;
    }

    public void setRootId(BigInteger rootId) {
        this.rootId = rootId;
    }

    public List<String> getTagCodes() {
        return tagCodes;
    }

    public void setTagCodes(List<String> tagCodes) {
        this.tagCodes = tagCodes;
    }

    public List<BigInteger> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<BigInteger> tagIds) {
        this.tagIds = tagIds;
    }

    public String getTagRelation() {
        return tagRelation;
    }

    public void setTagRelation(String tagRelation) {
        this.tagRelation = tagRelation;
    }

    public String getGlobalRelation() {
        return globalRelation;
    }

    public void setGlobalRelation(String globalRelation) {
        this.globalRelation = globalRelation;
    }
}
