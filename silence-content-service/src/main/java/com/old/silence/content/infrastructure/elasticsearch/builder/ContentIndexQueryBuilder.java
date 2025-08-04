package com.old.silence.content.infrastructure.elasticsearch.builder;


import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import com.old.silence.content.api.dto.ContentIndexQuery;
import com.old.silence.core.util.CollectionUtils;

/**
 * @author moryzang
 */
public class ContentIndexQueryBuilder {

    public static CriteriaQuery buildQuery(ContentIndexQuery contentIndexQuery) {
        Criteria criteria = new Criteria();

        // 添加type条件
        if (contentIndexQuery.getType() != null) {
            criteria = criteria.and("type").is(contentIndexQuery.getType());
        }
        // 精确匹配条件
        if (contentIndexQuery.getContentCode() != null) {
            criteria = criteria.and("contentCode").is(contentIndexQuery.getContentCode());
        }
        if (contentIndexQuery.getAuthor() != null) {
            criteria = criteria.and("author").is(contentIndexQuery.getAuthor());
        }
        if (contentIndexQuery.getLastModifiedBy() != null) {
            criteria = criteria.and("lastModifiedBy").is(contentIndexQuery.getLastModifiedBy());
        }
        if (contentIndexQuery.getAuditCode() != null) {
            criteria = criteria.and("auditCode").is(contentIndexQuery.getAuditCode());
        }
        if (contentIndexQuery.getCreatedBy() != null) {
            criteria = criteria.and("createdBy").is(contentIndexQuery.getCreatedBy());
        }
        if (contentIndexQuery.getType() != null) {
            criteria = criteria.and("type").is(contentIndexQuery.getType());
        }
        if (contentIndexQuery.getStatus() != null) {
            criteria = criteria.and("status").is(contentIndexQuery.getStatus());
        }
        if (contentIndexQuery.getParentId() != null) {
            criteria = criteria.and("parentId").is(contentIndexQuery.getParentId());
        }
        if (contentIndexQuery.getContentReferenceMode() != null) {
            criteria = criteria.and("contentReferenceMode").is(contentIndexQuery.getContentReferenceMode());
        }
        if (contentIndexQuery.getDisclosure() != null) {
            criteria = criteria.and("disclosure").is(contentIndexQuery.getDisclosure());
        }
        if (contentIndexQuery.getProductCode() != null) {
            criteria = criteria.and("productTerm.productCode").is(contentIndexQuery.getProductCode());
        }
        if (contentIndexQuery.getLeaf() != null) {
            criteria = criteria.and("leaf").is(contentIndexQuery.getLeaf());
        }
        if (contentIndexQuery.getRootId() != null) {
            criteria = criteria.and("rootId").is(contentIndexQuery.getRootId());
        }

        // 范围查询条件
        if (contentIndexQuery.getPublishedAtStart() != null || contentIndexQuery.getPublishedAtEnd() != null) {
            Criteria rangeCriteria = new Criteria("publishedAt");
            if (contentIndexQuery.getPublishedAtStart() != null) {
                rangeCriteria.greaterThanEqual(contentIndexQuery.getPublishedAtStart());
            }
            if (contentIndexQuery.getPublishedAtEnd() != null) {
                rangeCriteria.lessThanEqual(contentIndexQuery.getPublishedAtEnd());
            }
            criteria = criteria.and(rangeCriteria);
        }

        if (contentIndexQuery.getExpiredAtStart() != null || contentIndexQuery.getExpiredAtEnd() != null) {
            Criteria rangeCriteria = new Criteria("expiredAt");
            if (contentIndexQuery.getExpiredAtStart() != null) {
                rangeCriteria.greaterThanEqual(contentIndexQuery.getExpiredAtStart());
            }
            if (contentIndexQuery.getExpiredAtEnd() != null) {
                rangeCriteria.lessThanEqual(contentIndexQuery.getExpiredAtEnd());
            }
            criteria = criteria.and(rangeCriteria);
        }

        if (contentIndexQuery.getStickyTopExpiredAtStart() != null || contentIndexQuery.getStickyTopExpiredAtEnd() != null) {
            Criteria rangeCriteria = new Criteria("stickyTopExpiredAt");
            if (contentIndexQuery.getStickyTopExpiredAtStart() != null) {
                rangeCriteria.greaterThanEqual(contentIndexQuery.getStickyTopExpiredAtStart());
            }
            if (contentIndexQuery.getStickyTopExpiredAtEnd() != null) {
                rangeCriteria.lessThanEqual(contentIndexQuery.getStickyTopExpiredAtEnd());
            }
            criteria = criteria.and(rangeCriteria);
        }

        if (contentIndexQuery.getOnSaleAtStart() != null || contentIndexQuery.getOnSaleAtEnd() != null) {
            Criteria rangeCriteria = new Criteria("product_term.onSaleAt");
            if (contentIndexQuery.getOnSaleAtStart() != null) {
                rangeCriteria.greaterThanEqual(contentIndexQuery.getOnSaleAtStart());
            }
            if (contentIndexQuery.getOnSaleAtEnd() != null) {
                rangeCriteria.lessThanEqual(contentIndexQuery.getOnSaleAtEnd());
            }
            criteria = criteria.and(rangeCriteria);
        }

        // 多值查询条件
        if (CollectionUtils.isNotEmpty(contentIndexQuery.getContentTypes())) {
            criteria = criteria.and("type").in(contentIndexQuery.getContentTypes());
        }

        if (CollectionUtils.isNotEmpty(contentIndexQuery.getBusinessStatuses())) {
            criteria = criteria.and("businessStatus").in(contentIndexQuery.getBusinessStatuses());
        }

        if (CollectionUtils.isNotEmpty(contentIndexQuery.getTenantIds())) {
            criteria = criteria.and("tenantId").in(contentIndexQuery.getTenantIds());
        }

        //标签条件
        if (CollectionUtils.isNotEmpty(contentIndexQuery.getTagQueries())) {
            for (ContentIndexQuery.TagCondition condition : contentIndexQuery.getTagQueries()) {

                // 构建单个标签组的条件
                Criteria tagGroupCriteria = new Criteria()
                        .and(new Criteria("tags.tagType").is(condition.getTagType()));

                // 构建标签ID条件（OR/AND）
                Criteria tagIdCriteria = new Criteria();
                for (String tagId : condition.getTagIds()) {
                    if ("OR".equalsIgnoreCase(condition.getRelation())) {
                        tagIdCriteria.or("tags.tagId").is(tagId);
                    } else {
                        tagIdCriteria.and("tags.tagId").is(tagId);
                    }
                }

                // 组合条件
                Criteria nestedCriteria = tagGroupCriteria.and(tagIdCriteria);

                // 添加到主查询
                if ("OR".equalsIgnoreCase(contentIndexQuery.getGlobalRelation())) {
                    criteria.or(nestedCriteria);
                } else {
                    criteria.and(nestedCriteria);
                }
            }
        }

        return new CriteriaQuery(criteria);

    }
}
