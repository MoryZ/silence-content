package com.old.silence.content.infrastructure.elasticsearch.builder;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import com.old.silence.content.api.dto.ContentIndexQuery;
import com.old.silence.content.api.dto.TagCondition;
import com.old.silence.core.util.CollectionUtils;

/**
 * @author moryzang
 */
public class ContentIndexQueryBuilder {

    public NativeQuery buildQuery(ContentIndexQuery contentIndexQuery, Pageable pageable, List<TagCondition> tagConditions) {
        // 1. 创建BoolQuery构建器（对应原来的Criteria）
        BoolQuery.Builder boolBuilder = new BoolQuery.Builder();

        if (contentIndexQuery.getTitle() != null) {
            BoolQuery.Builder titleQuery = new BoolQuery.Builder();
            // 精确匹配（优先）
            titleQuery.should(s -> s.term(t -> t
                    .field("title.keyword")
                    .value(contentIndexQuery.getTitle())
                    .boost(2.0f)
            ));

            // 模糊匹配（后备）
            titleQuery.should(s -> s.match(m -> m
                    .field("title")
                    .query(contentIndexQuery.getTitle())
                    .analyzer("ik_smart")
                    .fuzziness("1")
            ));

            boolBuilder.must(m -> m.bool(titleQuery.build()));
         /*   boolBuilder.must(m -> m.term(t -> t
                    .field("title.keyword")
                    .value(contentIndexQuery.getTitle()) // 此时必须完全匹配
            ));*/
        }
        if (contentIndexQuery.getContentCode() != null) {
            boolBuilder.must(m -> m.term(t -> t.field("contentCode").value(contentIndexQuery.getContentCode())));
        }
        if (contentIndexQuery.getAuthor() != null) {
            boolBuilder.must(m -> m.match(t -> t.field("author").query(contentIndexQuery.getAuthor())));
        }
        if (contentIndexQuery.getLastModifiedBy() != null) {
            boolBuilder.must(m -> m.match(t -> t.field("lastModifiedBy").query(contentIndexQuery.getLastModifiedBy())));
        }
        if (contentIndexQuery.getAuditCode() != null) {
            boolBuilder.must(m -> m.term(t -> t.field("auditCode").value(contentIndexQuery.getAuditCode())));
        }
        if (contentIndexQuery.getCreatedBy() != null) {
            BoolQuery.Builder createdByQuery = new BoolQuery.Builder();
            // 精确匹配（优先）
            createdByQuery.should(s -> s.term(t -> t
                    .field("createdBy.keyword")
                    .value(contentIndexQuery.getTitle())
                    .boost(2.0f)
            ));

            // 模糊匹配（后备）
            createdByQuery.should(s -> s.match(m -> m
                    .field("createdBy")
                    .query(contentIndexQuery.getTitle())
                    .analyzer("ik_smart")
                    .fuzziness("1")
            ));

            boolBuilder.must(m -> m.bool(createdByQuery.build()));
        }
        if (contentIndexQuery.getType() != null) {
            boolBuilder.must(m -> m.term(t -> t.field("type").value(contentIndexQuery.getType())));
        }
        if (contentIndexQuery.getStatus() != null) {
            boolBuilder.must(m -> m.term(t -> t.field("status").value(contentIndexQuery.getStatus())));
        }
        if (contentIndexQuery.getParentId() != null) {
            boolBuilder.must(m -> m.term(t -> t.field("parentId").value(contentIndexQuery.getParentId().longValue())));
        }
        if (contentIndexQuery.getContentReferenceMode() != null) {
            boolBuilder.must(m -> m.term(t -> t.field("contentReferenceMode").value(contentIndexQuery.getContentReferenceMode())));
        }
        if (contentIndexQuery.getDisclosure() != null) {
            boolBuilder.must(m -> m.term(t -> t.field("disclosure").value(contentIndexQuery.getDisclosure())));
        }
        if (contentIndexQuery.getProductCode() != null) {
            boolBuilder.must(m -> m.term(t -> t.field("productTerm.productCode").value(contentIndexQuery.getProductCode())));
        }
        if (contentIndexQuery.getLeaf() != null) {
            boolBuilder.must(m -> m.term(t -> t.field("leaf").value(contentIndexQuery.getLeaf())));
        }
        if (contentIndexQuery.getRootId() != null) {
            boolBuilder.must(m -> m.term(t -> t.field("rootId").value(contentIndexQuery.getRootId().longValue())));
        }

        // 3. 范围查询（完全保留您的时间判断逻辑）
        if (contentIndexQuery.getPublishedAtStart() != null || contentIndexQuery.getPublishedAtEnd() != null) {
            RangeQuery.Builder rangeBuilder = new RangeQuery.Builder().field("publishedAt");
            if (contentIndexQuery.getPublishedAtStart() != null) {
                rangeBuilder.gte(JsonData.of(contentIndexQuery.getPublishedAtStart().toString()));
            }
            if (contentIndexQuery.getPublishedAtEnd() != null) {
                rangeBuilder.lte(JsonData.of(contentIndexQuery.getPublishedAtEnd().toString()));
            }
            boolBuilder.must(m -> m.range(rangeBuilder.build()));
        }

        if (contentIndexQuery.getExpiredAtStart() != null || contentIndexQuery.getExpiredAtEnd() != null) {
            RangeQuery.Builder rangeBuilder = new RangeQuery.Builder().field("expiredAt");
            if (contentIndexQuery.getExpiredAtStart() != null) {
                rangeBuilder.gte(JsonData.of(contentIndexQuery.getExpiredAtStart().toString()));
            }
            if (contentIndexQuery.getExpiredAtEnd() != null) {
                rangeBuilder.lte(JsonData.of(contentIndexQuery.getExpiredAtEnd().toString()));
            }
            boolBuilder.must(m -> m.range(rangeBuilder.build()));
        }

        if (contentIndexQuery.getStickyTopExpiredAtStart() != null || contentIndexQuery.getStickyTopExpiredAtEnd() != null) {
            RangeQuery.Builder rangeBuilder = new RangeQuery.Builder().field("stickyTopExpiredAt");
            if (contentIndexQuery.getStickyTopExpiredAtStart() != null) {
                rangeBuilder.gte(JsonData.of(contentIndexQuery.getStickyTopExpiredAtStart().toString()));
            }
            if (contentIndexQuery.getStickyTopExpiredAtEnd() != null) {
                rangeBuilder.lte(JsonData.of(contentIndexQuery.getStickyTopExpiredAtEnd().toString()));
            }
            boolBuilder.must(m -> m.range(rangeBuilder.build()));
        }

        if (contentIndexQuery.getOnSaleAtStart() != null || contentIndexQuery.getOnSaleAtEnd() != null) {
            RangeQuery.Builder rangeBuilder = new RangeQuery.Builder().field("productTerm.onSaleAt");
            if (contentIndexQuery.getOnSaleAtStart() != null) {
                rangeBuilder.gte(JsonData.of(contentIndexQuery.getOnSaleAtStart().toString()));
            }
            if (contentIndexQuery.getOnSaleAtEnd() != null) {
                rangeBuilder.lte(JsonData.of(contentIndexQuery.getOnSaleAtEnd().toString()));
            }
            boolBuilder.must(m -> m.range(rangeBuilder.build()));
        }

        // 4. 多值查询（完全保留您的CollectionUtils判断）
        if (CollectionUtils.isNotEmpty(contentIndexQuery.getContentTypes())) {
            boolBuilder.must(m -> m.terms(t -> t.field("type")
                    .terms(v -> v.value(contentIndexQuery.getContentTypes().stream()
                            .map(FieldValue::of)
                            .toList()))));
        }
        if (CollectionUtils.isNotEmpty(contentIndexQuery.getBusinessStatuses())) {
            boolBuilder.must(m -> m.terms(t -> t.field("businessStatus")
                    .terms(v -> v.value(contentIndexQuery.getBusinessStatuses().stream()
                            .map(FieldValue::of)
                            .toList()))));
        }
        if (CollectionUtils.isNotEmpty(contentIndexQuery.getTenantIds())) {
            boolBuilder.must(m -> m.terms(t -> t.field("tenantId")
                    .terms(v -> v.value(contentIndexQuery.getTenantIds().stream()
                            .map(FieldValue::of)
                            .toList()))));
        }

        // 2. 添加标签条件
        addTagConditions(boolBuilder,
                tagConditions,
                contentIndexQuery.getGlobalRelation());

        return NativeQuery.builder()
                .withQuery(q -> q.bool(boolBuilder.build()))
                .withPageable(pageable)
                .build();

    }

    private void buildTagConditionQuery(BoolQuery.Builder conditionBuilder, TagCondition condition) {
        if ("OR".equalsIgnoreCase(condition.getRelation())) {
            // 同tagType下OR关系：tags.tagId IN (id1,id2) AND tags.tagType=type
            conditionBuilder.must(m -> m.nested(n -> n
                    .path("tags")
                    .query(q -> q.bool(b -> b
                            .must(mu -> mu.term(t -> t.field("tags.tagType").value(condition.getTagType())))
                            .must(mu -> mu.terms(t -> t.field("tags.tagId")
                                    .terms(v -> v.value(condition.getTagIds().stream()
                                            .map(FieldValue::of)
                                            .toList())))
                            ))
                    )));
        } else {
            // 同tagType下AND关系：必须同时满足多个tagId（需要多个嵌套查询）
            condition.getTagIds().forEach(tagId -> {
                conditionBuilder.must(m -> m.nested(n -> n
                        .path("tags")
                        .query(q -> q.bool(b -> b
                                .must(mu -> mu.term(t -> t.field("tags.tagId").value(tagId)))
                                .must(mu -> mu.term(t -> t.field("tags.tagType").value(condition.getTagType())))
                        ))
                ));
            });
        }
    }

    private void addTagConditions(BoolQuery.Builder mainBoolBuilder,
                                  List<TagCondition> tagConditions,
                                  String globalRelation) {
        if (CollectionUtils.isEmpty(tagConditions)) return;

        // 临时Builder用于收集同类型条件
        BoolQuery.Builder combinedTagBuilder = new BoolQuery.Builder();

        // 1. 处理每个TagCondition的内部关系
        tagConditions.forEach(condition ->
                buildTagConditionQuery(combinedTagBuilder, condition)
        );

        // 2. 处理不同TagCondition之间的globalRelation
        if ("OR".equalsIgnoreCase(globalRelation)) {
            mainBoolBuilder.should(s -> s.bool(combinedTagBuilder.build()));
        } else {
            mainBoolBuilder.must(m -> m.bool(combinedTagBuilder.build()));
        }
    }
}
