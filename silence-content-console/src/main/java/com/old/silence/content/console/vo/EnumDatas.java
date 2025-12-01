package com.old.silence.content.console.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.old.silence.content.domain.enums.ContentReferenceMode;
import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.content.domain.enums.ContentType;
import com.old.silence.content.domain.enums.CoverImageReferenceMode;
import com.old.silence.core.enums.jackson.DescribedEnumValueSerializer;

/**
 * @author moryzang
 * 枚举数据容器
 */
public class EnumDatas {

    private static final EnumDatas INSTANCE = new EnumDatas();

    public static EnumDatas getInstance() {
        return INSTANCE;
    }

    @JsonSerialize(contentUsing = DescribedEnumValueSerializer.class)
    public final ContentType[] contentTypes = ContentType.values();

    @JsonSerialize(contentUsing = DescribedEnumValueSerializer.class)
    public final ContentStatus[] contentStatuses = ContentStatus.values();

    @JsonSerialize(contentUsing = DescribedEnumValueSerializer.class)
    public final ContentTagType[] contentTagTypes = ContentTagType.values();

    @JsonSerialize(contentUsing = DescribedEnumValueSerializer.class)
    public final ContentReferenceMode[] contentReferenceModes = ContentReferenceMode.values();

    @JsonSerialize(contentUsing = DescribedEnumValueSerializer.class)
    public final CoverImageReferenceMode[] coverImageReferenceModes = CoverImageReferenceMode.values();

    public ContentType[] getContentTypes() {
        return contentTypes;
    }

    public ContentStatus[] getContentStatuses() {
        return contentStatuses;
    }

    public ContentTagType[] getContentTagTypes() {
        return contentTagTypes;
    }

    public ContentReferenceMode[] getContentReferenceModes() {
        return contentReferenceModes;
    }

    public CoverImageReferenceMode[] getCoverImageReferenceModes() {
        return coverImageReferenceModes;
    }
}