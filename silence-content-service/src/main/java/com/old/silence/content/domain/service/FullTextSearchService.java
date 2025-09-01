package com.old.silence.content.domain.service;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import com.old.silence.content.infrastructure.elasticsearch.model.TextChunk;

import javax.naming.directory.SearchResult;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MurrayZhang
 */
@Service
public class FullTextSearchService {

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
        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery(QueryBuilders.multiMatch(builder -> query, "bookName^3", "content"))
                .withPageable(PageRequest.of(0, 10))
                .build();

        return elasticsearchOperations.search(searchQuery, TextChunk.class)
                .stream()
                .map(hit -> convertToSearchResult(hit.getContent()))
                .collect(Collectors.toList());
    }
}
