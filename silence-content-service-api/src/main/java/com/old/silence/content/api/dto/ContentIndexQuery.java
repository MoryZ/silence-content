package com.old.silence.content.api.dto;

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

    private Long parentId;

    private List<Byte> businessStatuses;

    private Byte contentReferenceMode;

    private List<Long> tenantIds;

    private Boolean disclosure;

    private String productCode;

    private Instant onSaleAtStart;

    private Instant onSaleAtEnd;

    private Boolean leaf;

    private Long rootId;

    private List<TagCondition> tagQueries;
    private String globalRelation;


    public static class TagCondition {
        private String tagType;
        private List<String> tagIds;
        private String relation;

        public String getTagType() {
            return tagType;
        }

        public void setTagType(String tagType) {
            this.tagType = tagType;
        }

        public List<String> getTagIds() {
            return tagIds;
        }

        public void setTagIds(List<String> tagIds) {
            this.tagIds = tagIds;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }
    }

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Byte> getBusinessStatuses() {
        return businessStatuses;
    }

    public void setBusinessStatuses(List<Byte> businessStatuses) {
        this.businessStatuses = businessStatuses;
    }

    public Byte getContentReferenceMode() {
        return contentReferenceMode;
    }

    public void setContentReferenceMode(Byte contentReferenceMode) {
        this.contentReferenceMode = contentReferenceMode;
    }

    public List<Long> getTenantIds() {
        return tenantIds;
    }

    public void setTenantIds(List<Long> tenantIds) {
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

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    public List<TagCondition> getTagQueries() {
        return tagQueries;
    }

    public void setTagQueries(List<TagCondition> tagQueries) {
        this.tagQueries = tagQueries;
    }

    public String getGlobalRelation() {
        return globalRelation;
    }

    public void setGlobalRelation(String globalRelation) {
        this.globalRelation = globalRelation;
    }
}
