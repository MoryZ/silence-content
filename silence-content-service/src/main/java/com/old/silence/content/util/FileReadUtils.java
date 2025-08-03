package com.old.silence.content.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;

/**
 * @author moryzang
 */
public final class FileReadUtils {
    private FileReadUtils() {
    }

    public static FileReadUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 读取JSON文件内容
     *
     * @param fileName 文件名，不需要带路径，默认在resources目录下
     * @return JSON字符串
     */
    public String readContent(String fileName) {
        try {
            // 构建文件的绝对路径
            ClassPathResource resource = new ClassPathResource("json/" + fileName);
            byte[] jsonData = Files.readAllBytes(resource.getFile().toPath());
            return new String(jsonData);
        } catch (IOException e) {
            throw new RuntimeException("读取文件失败：" + fileName, e);
        }
    }

    // 静态内部类实现单例模式
    private static class SingletonHolder {
        private static final FileReadUtils INSTANCE = new FileReadUtils();
    }
}
