package com.old.silence.content.api;

import java.util.List;

import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.infrastructure.elasticsearch.model.ContentDocument;
import com.old.silence.content.infrastructure.elasticsearch.service.ContentSearchService;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1/")
public class ContentIndexResource {
    private final ContentSearchService contentSearchService;

    public ContentIndexResource(ContentSearchService contentSearchService) {
        this.contentSearchService = contentSearchService;
    }

    @GetMapping("/contents/exists")
    public boolean searchContents(
            @RequestParam String indexName) {
        return contentSearchService.indexExists(indexName);
    }

    @GetMapping("/contents/search")
    public SearchPage<ContentDocument> searchContents(
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<Long> tagIds1,
            @RequestParam(required = false) List<Long> tagIds2,
            @RequestParam(required = false) List<Long> tagIds3,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean sortByPublished) {

        return contentSearchService.searchContents(
                type, status, title,
                tagIds1, tagIds2, tagIds3,
                page, size, sortByPublished);
    }


}
