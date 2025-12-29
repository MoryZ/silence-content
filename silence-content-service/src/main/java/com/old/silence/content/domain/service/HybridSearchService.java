package com.old.silence.content.domain.service;


import com.old.silence.content.api.vo.SearchResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author MurrayZhang
 */
//@Service
public class HybridSearchService {

    private final VectorSearchService vectorSearchService;
    private final FullTextSearchService fullTextSearchService;

    public HybridSearchService(VectorSearchService vectorSearchService,
                               FullTextSearchService fullTextSearchService) {
        this.vectorSearchService = vectorSearchService;
        this.fullTextSearchService = fullTextSearchService;
    }

    public Map<String, Object> hybridSearch(String query, int topK) {
        // 并行执行两种搜索
        CompletableFuture<List<Map<String, Object>>> vectorFuture = CompletableFuture
                .supplyAsync(() -> vectorSearchService.similaritySearch(query, topK));

        CompletableFuture<List<SearchResult>> fulltextFuture = CompletableFuture
                .supplyAsync(() -> fullTextSearchService.fullTextSearch(query));

        // 等待所有结果
        CompletableFuture.allOf(vectorFuture, fulltextFuture).join();

        // 结果融合和去重
        List<Map<String, Object>> vectorResults = vectorFuture.join();
        List<SearchResult> fulltextResults = fulltextFuture.join();

        return Map.of(
                "query", query,
                "vector_results", vectorResults,
                "fulltext_results", fulltextResults,
                "total_results", vectorResults.size() + fulltextResults.size()
        );
    }
}
