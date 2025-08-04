package com.old.silence.content.infrastructure.elasticsearch.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

/**
 * @author moryzang
 */
@Document(indexName = "content_wide_index")
public class ContentWideIndexDocument extends ContentIndexAccessor{

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String title;

    @Field(type = FieldType.Byte)
    private Byte type;

    @Field(type = FieldType.Byte)
    private Byte status;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String author;

    @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date publishedAt;

    @Field(type = FieldType.Keyword)
    private String contentCode;

    @Field(type = FieldType.Keyword)
    private String coverImageReference;

    @Field(type = FieldType.Integer)
    private Integer coverImageReferenceMode;

    @Field(type = FieldType.Integer)
    private Integer contentReferenceMode;

    @Field(type = FieldType.Text)
    private String contentReference;

    @Field(type = FieldType.Text)
    private String keywords;

    @Field(type = FieldType.Long)
    private Long tenantId;

    @Field(type = FieldType.Keyword)
    private String auditCode;

    @Field(type = FieldType.Keyword)
    private String taskCode;

    @Field(type = FieldType.Long)
    private Long parentId;

    @Field(type = FieldType.Long)
    private Long rootId;

    @Field(type = FieldType.Boolean)
    private Boolean stickyTop;

    @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date stickyTopAt;

    @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date stickyTopExpiredAt;

    @Field(type = FieldType.Boolean)
    private Boolean disclosure;

    @Field(type = FieldType.Boolean)
    private Boolean leaf;

    @Field(type = FieldType.Object)
    private Map<String, Object> attributes;

    @Field(type = FieldType.Integer)
    private Integer businessStatus;

    @Field(type = FieldType.Keyword)
    private String source;

    @Field(type = FieldType.Boolean)
    private Boolean codeDisplayRequired;

    @Field(type = FieldType.Boolean)
    private Boolean needInternalReview;

    @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date expiredAt;

    @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date createdDate;

    @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date lastModifiedDate;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String createdBy;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String lastModifiedBy;

    @Field(type = FieldType.Long)
    private Long previewAccumulation;

    @Field(type = FieldType.Long)
    private Long likeAccumulation;

    @Field(type = FieldType.Long)
    private Long forwardAccumulation;

    @Field(type = FieldType.Nested)
    private Article article;

    @Field(type = FieldType.Nested)
    private Live live;

    @Field(type = FieldType.Nested)
    private Video video;

    @Field(type = FieldType.Nested)
    private ProductTerm productTerm;

    @Field(type = FieldType.Nested)
    private List<Tag> tags;

    // 内部类定义嵌套对象
    public static class Article {
        @Field(type = FieldType.Text)
        private String summary;

        @Field(type = FieldType.Text)
        private String reprintDeclaration;

        @Field(type = FieldType.Byte)
        private Byte appliedToScenarioType;

        @Field(type = FieldType.Keyword)
        private String publisher;

        @Field(type = FieldType.Keyword)
        private String smallImageUrlReference;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getReprintDeclaration() {
            return reprintDeclaration;
        }

        public void setReprintDeclaration(String reprintDeclaration) {
            this.reprintDeclaration = reprintDeclaration;
        }

        public Byte getAppliedToScenarioType() {
            return appliedToScenarioType;
        }

        public void setAppliedToScenarioType(Byte appliedToScenarioType) {
            this.appliedToScenarioType = appliedToScenarioType;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getSmallImageUrlReference() {
            return smallImageUrlReference;
        }

        public void setSmallImageUrlReference(String smallImageUrlReference) {
            this.smallImageUrlReference = smallImageUrlReference;
        }
    }

    public static class Live {
        @Field(type = FieldType.Keyword)
        private String roomId;

        @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
        private Date startDate;

        @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
        private Date finishDate;

        @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
        private Date activeDate;

        @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
        private Date endDate;

        @Field(type = FieldType.Byte)
        private Byte liveStatus;

        @Field(type = FieldType.Keyword)
        private String tabularImageReference;

        @Field(type = FieldType.Byte)
        private Byte tabularImageReferenceMode;

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(Date finishDate) {
            this.finishDate = finishDate;
        }

        public Date getActiveDate() {
            return activeDate;
        }

        public void setActiveDate(Date activeDate) {
            this.activeDate = activeDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Byte getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(Byte liveStatus) {
            this.liveStatus = liveStatus;
        }

        public String getTabularImageReference() {
            return tabularImageReference;
        }

        public void setTabularImageReference(String tabularImageReference) {
            this.tabularImageReference = tabularImageReference;
        }

        public Byte getTabularImageReferenceMode() {
            return tabularImageReferenceMode;
        }

        public void setTabularImageReferenceMode(Byte tabularImageReferenceMode) {
            this.tabularImageReferenceMode = tabularImageReferenceMode;
        }
    }

    public static class Video {
        @Field(type = FieldType.Text)
        private String description;

        @Field(type = FieldType.Integer)
        private Integer duration;

        @Field(type = FieldType.Integer)
        private Integer width;

        @Field(type = FieldType.Integer)
        private Integer height;

        @Field(type = FieldType.Keyword)
        private String verticalCoverImageReference;

        @Field(type = FieldType.Keyword)
        private String scriptFilename;

        @Field(type = FieldType.Keyword)
        private String scriptFileKey;

        @Field(type = FieldType.Keyword)
        private String videoName;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getVerticalCoverImageReference() {
            return verticalCoverImageReference;
        }

        public void setVerticalCoverImageReference(String verticalCoverImageReference) {
            this.verticalCoverImageReference = verticalCoverImageReference;
        }

        public String getScriptFilename() {
            return scriptFilename;
        }

        public void setScriptFilename(String scriptFilename) {
            this.scriptFilename = scriptFilename;
        }

        public String getScriptFileKey() {
            return scriptFileKey;
        }

        public void setScriptFileKey(String scriptFileKey) {
            this.scriptFileKey = scriptFileKey;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }
    }

    public static class ProductTerm {
        @Field(name = "productCode", type = FieldType.Keyword)
        private String productCode;

        @Field(name = "onSaleAt", type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
        private Date onSaleAt;

        @Field(name = "offSaleAt", type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
        private Date offSaleAt;

        @Field(name = "displayOrder", type = FieldType.Long)
        private Long displayOrder;

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public Date getOnSaleAt() {
            return onSaleAt;
        }

        public void setOnSaleAt(Date onSaleAt) {
            this.onSaleAt = onSaleAt;
        }

        public Date getOffSaleAt() {
            return offSaleAt;
        }

        public void setOffSaleAt(Date offSaleAt) {
            this.offSaleAt = offSaleAt;
        }

        public Long getDisplayOrder() {
            return displayOrder;
        }

        public void setDisplayOrder(Long displayOrder) {
            this.displayOrder = displayOrder;
        }
    }

    public static class Tag {
        @Field(type = FieldType.Long)
        private Long tagId;

        @Field(type = FieldType.Keyword)
        private String tagCode;

        @Field(type = FieldType.Keyword)
        private String tagName;

        @Field(type = FieldType.Long)
        private Long tagSort;

        @Field(type = FieldType.Byte)
        private Byte tagType;


        public Long getTagId() {
            return tagId;
        }

        public void setTagId(Long tagId) {
            this.tagId = tagId;
        }

        public String getTagCode() {
            return tagCode;
        }

        public void setTagCode(String tagCode) {
            this.tagCode = tagCode;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public Long getTagSort() {
            return tagSort;
        }

        public void setTagSort(Long tagSort) {
            this.tagSort = tagSort;
        }

        public Byte getTagType() {
            return tagType;
        }

        public void setTagType(Byte tagType) {
            this.tagType = tagType;
        }
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getCoverImageReference() {
        return coverImageReference;
    }

    public void setCoverImageReference(String coverImageReference) {
        this.coverImageReference = coverImageReference;
    }

    public Integer getCoverImageReferenceMode() {
        return coverImageReferenceMode;
    }

    public void setCoverImageReferenceMode(Integer coverImageReferenceMode) {
        this.coverImageReferenceMode = coverImageReferenceMode;
    }

    public Integer getContentReferenceMode() {
        return contentReferenceMode;
    }

    public void setContentReferenceMode(Integer contentReferenceMode) {
        this.contentReferenceMode = contentReferenceMode;
    }

    public String getContentReference() {
        return contentReference;
    }

    public void setContentReference(String contentReference) {
        this.contentReference = contentReference;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getAuditCode() {
        return auditCode;
    }

    public void setAuditCode(String auditCode) {
        this.auditCode = auditCode;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    public Boolean getStickyTop() {
        return stickyTop;
    }

    public void setStickyTop(Boolean stickyTop) {
        this.stickyTop = stickyTop;
    }

    public Date getStickyTopAt() {
        return stickyTopAt;
    }

    public void setStickyTopAt(Date stickyTopAt) {
        this.stickyTopAt = stickyTopAt;
    }

    public Date getStickyTopExpiredAt() {
        return stickyTopExpiredAt;
    }

    public void setStickyTopExpiredAt(Date stickyTopExpiredAt) {
        this.stickyTopExpiredAt = stickyTopExpiredAt;
    }

    public Boolean getDisclosure() {
        return disclosure;
    }

    public void setDisclosure(Boolean disclosure) {
        this.disclosure = disclosure;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Integer getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(Integer businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getCodeDisplayRequired() {
        return codeDisplayRequired;
    }

    public void setCodeDisplayRequired(Boolean codeDisplayRequired) {
        this.codeDisplayRequired = codeDisplayRequired;
    }

    public Boolean getNeedInternalReview() {
        return needInternalReview;
    }

    public void setNeedInternalReview(Boolean needInternalReview) {
        this.needInternalReview = needInternalReview;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getPreviewAccumulation() {
        return previewAccumulation;
    }

    public void setPreviewAccumulation(Long previewAccumulation) {
        this.previewAccumulation = previewAccumulation;
    }

    public Long getLikeAccumulation() {
        return likeAccumulation;
    }

    public void setLikeAccumulation(Long likeAccumulation) {
        this.likeAccumulation = likeAccumulation;
    }

    public Long getForwardAccumulation() {
        return forwardAccumulation;
    }

    public void setForwardAccumulation(Long forwardAccumulation) {
        this.forwardAccumulation = forwardAccumulation;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public ProductTerm getProductTerm() {
        return productTerm;
    }

    public void setProductTerm(ProductTerm productTerm) {
        this.productTerm = productTerm;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}