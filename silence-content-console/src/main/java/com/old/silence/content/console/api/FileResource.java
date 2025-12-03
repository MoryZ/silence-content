package com.old.silence.content.console.api;

import jakarta.servlet.http.Part;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.console.vo.FileVo;
import com.old.silence.content.file.factory.FileStorageFactory;

import java.io.IOException;

/**
 *
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class FileResource {

    private final FileStorageFactory fileStorageFactory;

    public FileResource(FileStorageFactory fileStorageFactory) {
        this.fileStorageFactory = fileStorageFactory;
    }

    @PostMapping("/files/upload")
    public FileVo create(@RequestParam Part part) throws IOException {
        var filename = part.getSubmittedFileName();
        var storageTemplate = fileStorageFactory.getStorageTemplate();
        var fileKey = storageTemplate.upload(filename, part.getInputStream());
        return new FileVo(filename, fileKey, storageTemplate.getPreviewUrl(fileKey));
    }
}
