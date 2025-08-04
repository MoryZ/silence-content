package com.old.silence.content.api;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.CriteriaQueryBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.dto.ContentIndexCommand;
import com.old.silence.content.api.dto.ContentIndexQuery;
import com.old.silence.content.infrastructure.elasticsearch.builder.ContentIndexQueryBuilder;
import com.old.silence.content.infrastructure.elasticsearch.model.ContentWideIndexDocument;
import com.old.silence.content.infrastructure.elasticsearch.service.ContentSearchService;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class ContentIndexResource {
    private final ContentSearchService contentSearchService;

    public ContentIndexResource(ContentSearchService contentSearchService) {
        this.contentSearchService = contentSearchService;
    }

    @GetMapping("/contentIndexes")
    public boolean exists(
            @RequestParam String indexName) {
        return contentSearchService.indexExists(indexName);
    }

    @GetMapping("/contentIndexes/detail")
    public Map<String, Object> detail(
            @RequestParam String indexName) {
        return contentSearchService.getIndexStats(indexName);
    }

    @PostMapping("/contentIndexes")
    public boolean create(@RequestBody ContentIndexCommand contentIndexCommand) {
        return contentSearchService.create(contentIndexCommand);
    }


    @GetMapping(value = "/contentIndexes", params = {"pageNo", "pageSize"})
    public SearchPage<ContentWideIndexDocument> queryPage(ContentIndexQuery contentIndexQuery, Pageable pageable) {
        var criteriaQuery = ContentIndexQueryBuilder.buildQuery(contentIndexQuery);
        return contentSearchService.queryPage(criteriaQuery, pageable);
    }

    @DeleteMapping("/contentIndexes")
    public boolean delete(@RequestParam String indexName) {
        return contentSearchService.delete(indexName);
    }

}
