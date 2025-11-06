package com.old.silence.content.console.api;

import jakarta.servlet.http.Part;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.autoconfigure.cos.CosTemplate;

import java.io.IOException;

/**
 *
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class FileResource {
  /*  private final MinioTemplate minioTemplate;

    public FileResource(MinioTemplate minioTemplate) {
        this.minioTemplate = minioTemplate;
    }

    @PostMapping("/files/upload")
    public String create(@RequestParam Part part) throws IOException {
        return minioTemplate.upload(part.getSubmittedFileName(), part.getInputStream());
    }*/

    private final CosTemplate cosTemplate;

    public FileResource(CosTemplate cosTemplate) {
        this.cosTemplate = cosTemplate;
    }

    @PostMapping("/files/upload")
    public String create(@RequestParam Part part) throws IOException {
        return cosTemplate.upload(part.getSubmittedFileName(), part.getInputStream());
    }
}
