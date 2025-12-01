package com.old.silence.content.console.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.ContentArticleClient;
import com.old.silence.content.console.vo.ContentArticleConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class ContentArticleResource {

    private final ContentArticleClient contentArticleClient;

    public ContentArticleResource(ContentArticleClient contentArticleClient) {
        this.contentArticleClient = contentArticleClient;
    }

    @GetMapping("/contentArticles/{id}")
    public ContentArticleConsoleView findById(@PathVariable BigInteger id) {
        return contentArticleClient.findById(id, ContentArticleConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

}
