package com.old.silence.content.infrastructure.elasticsearch.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.dto.ContentIndexCommand;
import com.old.silence.content.infrastructure.elasticsearch.enums.EsIndexType;
import com.old.silence.content.infrastructure.elasticsearch.model.ContentWideIndexDocument;
import com.old.silence.core.enums.EnumValueFactory;

/**
 * @author moryzang
 */
@Service
public class ContentSearchService {


    /*private final ContentIndexRepository contentIndexRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public ContentSearchService(ContentIndexRepository contentIndexRepository,
                                ElasticsearchOperations elasticsearchOperations) {
        this.contentIndexRepository = contentIndexRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    *//**
     * @param indexName 索引名称
     * @return 判断结果
     *//*
    public boolean indexExists(String indexName) {
        return elasticsearchOperations.indexOps(IndexCoordinates.of(indexName)).exists();
    }

    *//**
     * @param indexName 索引名称
     * @return 索引详情
     *//*
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

    *//**
     * 复杂查询方法
     *
     * @param nativeQuery            内容查询条件
     * @return 分页结果
     *//*
    public SearchPage<ContentWideIndexDocument> queryPage(NativeQuery nativeQuery) {

        //  执行查询
        SearchHits<ContentWideIndexDocument> searchHits = elasticsearchOperations.search(nativeQuery, ContentWideIndexDocument.class);

        //  转换为Page对象
        return SearchHitSupport.searchPageFor(searchHits, nativeQuery.getPageable());
    }

    *//**
     * 使用Repository的简单查询示例
     *//*
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

    }*/
}