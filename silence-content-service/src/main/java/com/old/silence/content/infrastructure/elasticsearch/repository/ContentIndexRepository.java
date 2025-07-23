package com.old.silence.content.infrastructure.elasticsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.old.silence.content.infrastructure.elasticsearch.model.ContentDocument;

/**
 * @author moryzang
 */

public interface ContentIndexRepository extends ElasticsearchRepository<ContentDocument, Long> {

    Page<ContentDocument> findByTitle(String title, Pageable pageable);     // 基础CRUD方法由Spring Data自动实现

}