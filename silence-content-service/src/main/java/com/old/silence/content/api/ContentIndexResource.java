package com.old.silence.content.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.dto.ContentIndexCommand;
import com.old.silence.content.api.dto.ContentIndexQuery;
import com.old.silence.content.api.dto.TagCondition;
import com.old.silence.content.api.vo.ContentTagBasicView;
import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.content.domain.repository.ContentTagRepository;
import com.old.silence.content.infrastructure.elasticsearch.builder.ContentIndexQueryBuilder;
import com.old.silence.content.infrastructure.elasticsearch.model.ContentWideIndexDocument;
import com.old.silence.content.infrastructure.elasticsearch.service.ContentSearchService;
import com.old.silence.core.util.CollectionUtils;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class ContentIndexResource {
    private final ContentSearchService contentSearchService;
    private final ContentTagRepository contentTagRepository;

    public ContentIndexResource(ContentSearchService contentSearchService,
                                ContentTagRepository contentTagRepository) {
        this.contentSearchService = contentSearchService;
        this.contentTagRepository = contentTagRepository;
    }

    /*@GetMapping("/contentIndexes")
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

        List<ContentTagBasicView> contentTags = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(contentIndexQuery.getTagCodes())) {
            contentTags = contentTagRepository.findByEnabledAndCodesIn(true, contentIndexQuery.getTagCodes(), ContentTagBasicView.class);

        }
        if (CollectionUtils.isNotEmpty(contentIndexQuery.getTagIds())) {
            contentTags = contentTagRepository.findByIds(contentIndexQuery.getTagIds(), ContentTagBasicView.class);
        }
        var tagConditions = new ArrayList<TagCondition>();
        var contentTagTypeCollectionMap = CollectionUtils.groupingBy(contentTags, ContentTagBasicView::getType);
        for (Map.Entry<ContentTagType, Collection<ContentTagBasicView>> contentTagTypeCollectionEntry : contentTagTypeCollectionMap.entrySet()) {
            var tagCondition = TagCondition.buildTagCondition(contentTagTypeCollectionEntry.getKey(),
                    CollectionUtils.transformToList(contentTagTypeCollectionEntry.getValue(), ContentTagBasicView::getId),
                    contentIndexQuery.getTagRelation());
            tagConditions.add(tagCondition);
        }

        var nativeQuery = new ContentIndexQueryBuilder().buildQuery(contentIndexQuery, pageable, tagConditions);
        return contentSearchService.queryPage(nativeQuery);
    }

    @DeleteMapping("/contentIndexes")
    public boolean delete(@RequestParam String indexName) {
        return contentSearchService.delete(indexName);
    }*/

}
