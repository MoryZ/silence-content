package com.old.silence.content.console.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.ContentVideoClient;
import com.old.silence.content.console.vo.ContentVideoConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class ContentVideoResource {

    private final ContentVideoClient contentVideoClient;

    public ContentVideoResource(ContentVideoClient contentVideoClient) {
        this.contentVideoClient = contentVideoClient;
    }

    @GetMapping("/contentVideos/{id}")
    public ContentVideoConsoleView findById(@PathVariable BigInteger id) {
        return contentVideoClient.findById(id, ContentVideoConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

}
