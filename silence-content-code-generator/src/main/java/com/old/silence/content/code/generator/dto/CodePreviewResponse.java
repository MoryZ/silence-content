package com.old.silence.content.code.generator.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码预览响应
 *
 * @author moryzang
 */
public class CodePreviewResponse {

    /**
     * 表名
     */
    private String tableName;


    /**
     * 生成的文件列表
     */
    private List<GeneratedFile> files = new ArrayList<>();

    /**
     * 生成的文件信息
     */
    public static class GeneratedFile {
        /**
         * 模块名称（interface/service/console/enum）
         */
        private String module;

        /**
         * 文件名
         */
        private String fileName;

        /**
         * 文件路径（相对路径）
         */
        private String filePath;

        /**
         * 文件内容
         */
        private String content;

        /**
         * 文件类型（dto/vo/mapper/dao/repository/resource等）
         */
        private String fileType;

        public GeneratedFile() {
        }

        public GeneratedFile(String module, String fileName, String filePath, String content, String fileType) {
            this.module = module;
            this.fileName = fileName;
            this.filePath = filePath;
            this.content = content;
            this.fileType = fileType;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<GeneratedFile> getFiles() {
        return files;
    }

    public void setFiles(List<GeneratedFile> files) {
        this.files = files;
    }

    public void addFile(GeneratedFile file) {
        this.files.add(file);
    }

    public void addFile(String module, String fileName, String filePath, String content, String fileType) {
        this.files.add(new GeneratedFile(module, fileName, filePath, content, fileType));
    }
}
