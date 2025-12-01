package com.old.silence.content.file.factory;

import java.io.InputStream;

import com.old.silence.content.file.enums.StorageType;

/**
 * @author moryzang
 */
public interface FileStorageStrategy {


    StorageType getStorageType();

    /**
     * 上传文件
     */
    String upload(String filename, String content);

    /**
     * 上传文件
     */
    String upload(String filename, InputStream inputStream);

    /**
     * 下载文件
     */
    InputStream downloadFile(String fileKey, String filename);

    /**
     * 删除文件
     */
    void deleteFile(String fileKey);

    /**
     * 获取文件访问URL
     */
    String getPreviewUrl(String fileKey);

    /**
     * 文件是否存在
     */
    boolean fileKeyExists(String fileKey);
}
