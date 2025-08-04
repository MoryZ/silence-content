package com.old.silence.content.infrastructure.elasticsearch.service;

import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.dto.ContentIndexCommand;
import com.old.silence.content.api.dto.ContentIndexQuery;
import com.old.silence.content.infrastructure.elasticsearch.enums.EsIndexType;
import com.old.silence.content.infrastructure.elasticsearch.model.ContentWideIndexDocument;
import com.old.silence.content.infrastructure.elasticsearch.repository.ContentIndexRepository;
import com.old.silence.core.enums.EnumValueFactory;

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
        return elasticsearchOperations.indexOps(IndexCoordinates.of(indexName)).exists();
    }

    /**
     * @param indexName 索引名称
     * @return 索引详情
     */
    public Map<String, Object> getIndexStats(String indexName) {
        IndexOperations indexOps = elasticsearchOperations.indexOps(IndexCoordinates.of(indexName));

        Map<String, Object> info = new HashMap<>();
        info.put("exists", indexOps.exists());

        if (indexOps.exists()) {
            info.put("settings", indexOps.getSettings());
            info.put("mappings", indexOps.getMapping());
            info.put("aliases", indexOps.getAliases());
        }

        return info;
    }

    /**
     * 复杂查询方法
     *
     * @param criteriaQuery            内容查询条件
     * @param pageable          分页参数
     * @return 分页结果
     */
    public SearchPage<ContentWideIndexDocument> queryPage(CriteriaQuery criteriaQuery, Pageable pageable) {

        // 3. 构建查询
        Query query = criteriaQuery.setPageable(pageable);

        // 4. 执行查询
        SearchHits<ContentWideIndexDocument> searchHits = elasticsearchOperations.search(query, ContentWideIndexDocument.class);

        // 5. 转换为Page对象
        return SearchHitSupport.searchPageFor(searchHits, query.getPageable());
    }

    /**
     * 使用Repository的简单查询示例
     */
    public Page<ContentWideIndexDocument> simpleSearch(String title, Pageable pageable) {
        return contentIndexRepository.findContentWideIndexDocumentByTitle(title, pageable);
    }

    public boolean create(ContentIndexCommand contentIndexCommand) {

        var esIndexType = EnumValueFactory.getRequired(EsIndexType.class, contentIndexCommand.getEsIndexType());
        return elasticsearchOperations.indexOps(esIndexType.getContentAccessorClass()).create();
    }

    public boolean delete(String indexName) {

        IndexOperations indexOps = elasticsearchOperations.indexOps(IndexCoordinates.of(indexName));

        var exists = indexOps.exists();
        if (exists) {
            return indexOps.delete();
        }
        throw new ResourceNotFoundException("indexName not exists");

    }
}