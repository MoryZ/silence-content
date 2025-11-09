package com.old.silence.content.console.vo;

/**
 * @author moryzang
 */
public class FileVo {

    private String filename;
    private String fileKey;
    private String url;

    public FileVo() {
    }

    public FileVo(String filename, String fileKey, String url) {
        this.filename = filename;
        this.fileKey = fileKey;
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
