package com.old.silence.content.infrastructure.elasticsearch.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * @author moryzang
 */
@Document(indexName = "content_wide_index")
@Setting(settingPath = "/elasticsearch/settings/custom-analyzer.json")
public class ContentWideIndexDocument extends ContentIndexAccessor {


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

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time,
            pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Instant publishedAt;

    @Field(type = FieldType.Keyword)
    private String contentCode;

    @Field(type = FieldType.Keyword, index = false)
    private String coverImageReference;

    @Field(type = FieldType.Integer, index = false)
    private Integer coverImageReferenceMode;

    @Field(type = FieldType.Integer)
    private Integer contentReferenceMode;

    @Field(type = FieldType.Text, index = false)
    private String contentReference;

    @Field(type = FieldType.Text, index = false)
    private String keywords;

    @Field(type = FieldType.Long)
    private Long parentId;

    @Field(type = FieldType.Long)
    private Long rootId;

    @Field(type = FieldType.Boolean)
    private Boolean stickyTop;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time,
            pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Instant stickyTopAt;

    @Field(type = FieldType.Object)
    private Map<String, Object> attributes;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time,
            pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Instant expiredAt;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time,
            pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Instant createdDate;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time,
            pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Instant lastModifiedDate;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "alphanum_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String createdBy;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "alphanum_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
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
    private Video video;


    @Field(type = FieldType.Nested)
    private List<Tag> tags;

    // 内部类定义嵌套对象
    public static class Article {
        @Field(type = FieldType.Text, index = false)
        private String summary;

        @Field(type = FieldType.Text, index = false)
        private String reprintDeclaration;

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

    }

    public static class Video {
        @Field(type = FieldType.Keyword, index = false)
        private String description;

        @Field(type = FieldType.Integer, index = false)
        private Integer duration;

        @Field(type = FieldType.Integer, index = false)
        private Integer width;

        @Field(type = FieldType.Integer, index = false)
        private Integer height;

        @Field(type = FieldType.Keyword, index = false)
        private String verticalCoverImageReference;

        @Field(type = FieldType.Keyword, index = false)
        private String scriptFilename;

        @Field(type = FieldType.Keyword, index = false)
        private String scriptFileKey;

        @Field(type = FieldType.Keyword, index = false)
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


    public static class Tag {
        @Field(type = FieldType.Long)
        private Long tagId;

        @Field(type = FieldType.Keyword)
        private String tagCode;

        @Field(type = FieldType.Keyword, index = false)
        private String tagName;

        @Field(type = FieldType.Long, index = false)
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

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
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

    public Instant getStickyTopAt() {
        return stickyTopAt;
    }

    public void setStickyTopAt(Instant stickyTopAt) {
        this.stickyTopAt = stickyTopAt;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
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

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}