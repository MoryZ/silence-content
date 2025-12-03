package com.old.silence.content.domain.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author MurrayZhang
 */
@Service
public class FileStorageService {

   /* private final MinioTemplate minioTemplate;

    public FileStorageService(MinioTemplate minioTemplate) {
        this.minioTemplate = minioTemplate;
    }

    public String uploadFile(InputStream inputStream, String filename) {
        var fileKey = minioTemplate.upload(filename, inputStream);

        return minioTemplate.getInternetUrl(fileKey, filename);
    }*/

}
