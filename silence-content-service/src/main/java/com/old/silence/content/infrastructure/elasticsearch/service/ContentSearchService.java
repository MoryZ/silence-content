package com.old.silence.content.infrastructure.elasticsearch.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import com.old.silence.content.infrastructure.elasticsearch.model.ContentDocument;
import com.old.silence.content.infrastructure.elasticsearch.repository.ContentIndexRepository;

/**
 * @author moryzang
 */
@Service
public class ContentSearchService {

    private final ContentIndexRepository contentIndexRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public ContentSearchService(ContentIndexRepository contentIndexRepository,
                                ElasticsearchOperations elasticsearchOperations) {
        this.contentIndexRepository = contentIndexRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * @param indexName 索引名称
     * @return 判断结果
     */
    public boolean indexExists(String indexName) {
        return elasticsearchOperations.indexOps(ContentDocument.class).exists();
    }

    /**
     * 复杂查询方法
     *
     * @param type            内容类型
     * @param status          内容状态
     * @param title           标题关键词
     * @param tagIds1         类型1的标签ID列表
     * @param tagIds2         类型2的标签ID列表
     * @param tagIds3         类型3的标签ID列表
     * @param page            页码
     * @param size            每页大小
     * @param sortByPublished 是否按发布时间排序(true:按发布时间, false:按更新时间)
     * @return 分页结果
     */
    public SearchPage<ContentDocument> searchContents(
            Integer type,
            Integer status,
            String title,
            List<Long> tagIds1,
            List<Long> tagIds2,
            List<Long> tagIds3,
            int page,
            int size,
            boolean sortByPublished) {

        // 1. 构建Criteria
        Criteria criteria = new Criteria();

        // 添加type条件
        if (type != null) {
            criteria = criteria.and("type").is(type);
        }

        // 添加status条件
        if (status != null) {
            criteria = criteria.and("status").is(status);
        }

        // 添加title条件（使用模糊匹配）
        if (StringUtils.isNotBlank(title)) {
            criteria = criteria.and("title").matches(title);
        }

        // 添加标签条件
        // 创建一个空的AND条件列表
        List<Criteria> andConditions = new ArrayList<>();

        // 添加标签条件（如果提供了对应标签）
        if (tagIds1 != null && !tagIds1.isEmpty()) {
            andConditions.add(Criteria.where("tagIds1").in(tagIds1));
        }

        if (tagIds2 != null && !tagIds2.isEmpty()) {
            andConditions.add(Criteria.where("tagIds2").in(tagIds2));
        }

        if (tagIds3 != null && !tagIds3.isEmpty()) {
            andConditions.add(Criteria.where("tagIds3").in(tagIds3));
        }

        // 只有当至少有一个标签条件时才添加到主条件中
        if (!andConditions.isEmpty()) {
            criteria = criteria.and(andConditions.toArray(new Criteria[0]));
        }


        // 2. 构建排序
        Sort sort = Sort.by(
                Sort.Order.desc("is_sticky_top"),
                sortByPublished ?
                        Sort.Order.desc("published_at") :
                        Sort.Order.desc("updated_date")
        );

        // 3. 构建查询
        Query query = new CriteriaQuery(criteria)
                .addSort(sort)
                .setPageable(PageRequest.of(page, size));

        // 4. 执行查询
        SearchHits<ContentDocument> searchHits = elasticsearchOperations.search(query, ContentDocument.class);

        // 5. 转换为Page对象
        return SearchHitSupport.searchPageFor(searchHits, query.getPageable());
    }

    /**
     * 使用Repository的简单查询示例
     */
    public Page<ContentDocument> simpleSearch(String title, int page, int size) {
        return contentIndexRepository.findByTitle(title, PageRequest.of(page, size));
    }
}