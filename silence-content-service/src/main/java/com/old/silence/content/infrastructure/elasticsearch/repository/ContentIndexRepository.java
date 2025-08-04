package com.old.silence.content.infrastructure.elasticsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.old.silence.content.infrastructure.elasticsearch.model.ContentWideIndexDocument;

/**
 * @author moryzang
 */

public interface ContentIndexRepository extends ElasticsearchRepository<ContentWideIndexDocument, Long> {

    Page<ContentWideIndexDocument> findContentWideIndexDocumentByTitle(String title, Pageable pageable);

}