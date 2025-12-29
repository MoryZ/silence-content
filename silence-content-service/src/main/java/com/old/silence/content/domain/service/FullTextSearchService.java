package com.old.silence.content.domain.service;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.vo.SearchResult;
import com.old.silence.content.infrastructure.elasticsearch.model.TextChunk;
import com.old.silence.core.util.CollectionUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MurrayZhang
 */
@Service
public class FullTextSearchService {


    private static final Logger log = LoggerFactory.getLogger(FullTextSearchService.class);
    private final ElasticsearchOperations elasticsearchOperations;

    public FullTextSearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public void indexDocument(BigInteger fileId, String filename, List<String> chunks) {
        List<TextChunk> documents = new ArrayList<>();
        for (int i = 0; i < chunks.size(); i++) {
            TextChunk chunk = new TextChunk();
            chunk.setId(fileId + "_" + i);
            chunk.setFileId(String.valueOf(fileId));
            chunk.setBookName(filename);
            chunk.setContent(chunks.get(i));
            chunk.setChunkIndex(i);
            documents.add(chunk);
        }

        elasticsearchOperations.save(documents);
    }

    public List<SearchResult> fullTextSearch(String query) {
        try {
            if (StringUtils.isBlank(query)) {
                return Collections.emptyList();
            }

            NativeQuery searchQuery = new NativeQueryBuilder()
                    .withQuery(q -> q
                            .multiMatch(mm -> mm
                                    .query(query)
                                    .fields("bookName^3", "content")
                                    .fuzziness("AUTO")
                            )
                    )
                    .withPageable(PageRequest.of(0, 10))
                    .build();

            SearchHits<TextChunk> searchHits = elasticsearchOperations.search(searchQuery, TextChunk.class);

            return searchHits.stream()
                    .map(this::convertToSearchResultWithHighlight)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("全文搜索失败，查询: {}", query, e);
            return Collections.emptyList();
        }
    }

    private SearchResult convertToSearchResultWithHighlight(SearchHit<TextChunk> hit) {
        TextChunk content = hit.getContent();
        SearchResult result = convertToSearchResult(content);
        result.setScore(hit.getScore());

        // 处理高亮
        Map<String, List<String>> highlightFields = hit.getHighlightFields();
        if (CollectionUtils.isNotEmpty(highlightFields)) {
            highlightFields.forEach((field, highlights) -> {
                if (!highlights.isEmpty()) {
                    switch (field) {
                        case "bookName":
                            result.setHighlightedBookName(highlights.get(0));
                            break;
                        case "content":
                            result.setHighlightedContent(highlights.get(0));
                            break;
                    }
                }
            });
        }

        return result;
    }

    private SearchResult convertToSearchResult(TextChunk textChunk) {
        if (textChunk == null) {
            return null;
        }

        SearchResult result = new SearchResult();
        result.setId(textChunk.getId());
        result.setBookName(textChunk.getBookName());
        result.setContent(textChunk.getContent());

        // 根据您的 TextChunk 类的实际字段进行设置
        if (textChunk.getAuthor() != null) {
            result.setAuthor(textChunk.getAuthor());
        }
        if (textChunk.getPublishDate() != null) {
            result.setPublishDate(textChunk.getPublishDate());
        }
        if (textChunk.getCategory() != null) {
            result.setCategory(textChunk.getCategory());
        }

        return result;
    }
}
