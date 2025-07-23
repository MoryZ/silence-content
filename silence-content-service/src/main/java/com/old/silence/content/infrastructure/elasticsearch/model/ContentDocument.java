package com.old.silence.content.infrastructure.elasticsearch.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

/**
 * @author moryzang
 */
@Document(indexName = "content_index")
public class ContentDocument {

    @Id
    private Long id;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String title;

    @Field(type = FieldType.Integer)
    private Integer type;

    @Field(type = FieldType.Integer)
    private Integer status;

    @Field(type = FieldType.Date, name = "published_at")
    private String publishedAt;

    @Field(type = FieldType.Boolean, name = "is_sticky_top")
    private Boolean isStickyTop;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Keyword, name = "content_code")
    private String contentCode;

    @Field(type = FieldType.Keyword, name = "cover_image_reference")
    private String coverImageReference;

    @Field(type = FieldType.Integer, name = "cover_image_reference_mode")
    private Integer coverImageReferenceMode;

    @Field(type = FieldType.Integer, name = "content_reference_mode")
    private Integer contentReferenceMode;

    @Field(type = FieldType.Keyword, name = "content_reference")
    private String contentReference;


    @Field(type = FieldType.Nested, name = "section")
    private List<TagInfo> section;  // 类型1的标签

    @Field(type = FieldType.Nested, name = "branch")
    private List<TagInfo> branch;  // 类型2的标签

    @Field(type = FieldType.Nested, name = "channel")
    private List<TagInfo> channel;  // 类型3的标签

    @Field(type = FieldType.Nested, name = "article_info")
    private ArticleInfo articleInfo;

    @Field(type = FieldType.Nested, name = "live_info")
    private LiveInfo liveInfo;

    @Field(type = FieldType.Nested, name = "video_info")
    private VideoInfo videoInfo;

    @Field(type = FieldType.Date)
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Boolean getStickyTop() {
        return isStickyTop;
    }

    public void setStickyTop(Boolean stickyTop) {
        isStickyTop = stickyTop;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public List<TagInfo> getSection() {
        return section;
    }

    public void setSection(List<TagInfo> section) {
        this.section = section;
    }

    public List<TagInfo> getBranch() {
        return branch;
    }

    public void setBranch(List<TagInfo> branch) {
        this.branch = branch;
    }

    public List<TagInfo> getChannel() {
        return channel;
    }

    public void setChannel(List<TagInfo> channel) {
        this.channel = channel;
    }

    public ArticleInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(ArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }

    public LiveInfo getLiveInfo() {
        return liveInfo;
    }

    public void setLiveInfo(LiveInfo liveInfo) {
        this.liveInfo = liveInfo;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    // 嵌套类型定义
    public static class ArticleInfo {
        @Field(type = FieldType.Text, analyzer = "ik_smart")
        private String summary;

        @Field(type = FieldType.Text, analyzer = "ik_smart", name = "reprint_declaration")
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

    public static class LiveInfo {
        @Field(type = FieldType.Keyword, name = "room_id")
        private String roomId;

        @Field(type = FieldType.Date, name = "started_date")
        private String startedDate;

        @Field(type = FieldType.Date, name = "finish_date")
        private String finishDate;

        @Field(type = FieldType.Date, name = "active_date")
        private String activeDate;

        @Field(type = FieldType.Date, name = "end_Date")
        private String endDate;

        @Field(type = FieldType.Integer, name = "live_status")
        private Integer liveStatus;

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getStartedDate() {
            return startedDate;
        }

        public void setStartedDate(String startedDate) {
            this.startedDate = startedDate;
        }

        public String getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(String finishDate) {
            this.finishDate = finishDate;
        }

        public String getActiveDate() {
            return activeDate;
        }

        public void setActiveDate(String activeDate) {
            this.activeDate = activeDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public Integer getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(Integer liveStatus) {
            this.liveStatus = liveStatus;
        }
    }

    public static class VideoInfo {
        @Field(type = FieldType.Integer)
        private Integer duration;

        @Field(type = FieldType.Integer)
        private Integer width;

        @Field(type = FieldType.Integer)
        private Integer height;

        @Field(type = FieldType.Text, analyzer = "ik_smart")
        private String description;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class TagInfo {
        private Long id;
        private String code;
        private String name;
        private int type;
        private long sort;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getSort() {
            return sort;
        }

        public void setSort(long sort) {
            this.sort = sort;
        }
    }

}