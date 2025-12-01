package com.old.silence.content.console.api;

import jakarta.servlet.http.Part;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.console.vo.FileVo;

import java.io.IOException;

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
    public FileVo create(@RequestParam Part part) throws IOException {
        var filename = part.getSubmittedFileName();
        var fileKey = minioTemplate.upload(filename, part.getInputStream());
        return new FileVo(filename, fileKey, minioTemplate.getInternetUrl(fileKey));
    }
}
