package com.old.silence.content.api.dto;

import java.math.BigInteger;
import java.util.List;

import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.core.util.CollectionUtils;

/**
 * @author moryzang
 */
public class TagCondition {

    private Byte tagType;
    private List<Long> tagIds;
    private String relation;

    public static TagCondition buildTagCondition(ContentTagType tagType, List<BigInteger> tagIds, String relation) {
        var tagCondition = new TagCondition();
        tagCondition.setTagType(tagType.getValue());
        tagCondition.setTagIds(CollectionUtils.transformToList(tagIds, BigInteger::longValue));
        tagCondition.setRelation(relation);
        return tagCondition;
    }

    public Byte getTagType() {
        return tagType;
    }

    public void setTagType(Byte tagType) {
        this.tagType = tagType;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
