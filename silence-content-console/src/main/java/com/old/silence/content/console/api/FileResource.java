package com.old.silence.content.console.api;

import jakarta.servlet.http.Part;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.autoconfigure.minio.MinioTemplate;


/**
 *
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class FileResource {
    private final MinioTemplate minioTemplate;

    public FileResource(MinioTemplate minioTemplate) {
        this.minioTemplate = minioTemplate;
    }

    @PostMapping("/files/upload")
    public String create(@RequestParam Part part) throws IOException {
        return minioTemplate.upload(part.getSubmittedFileName(), part.getInputStream());
    }

}
