package com.old.silence.code.generator.config;

import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.NonNull;

/**
 * @author moryzang
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @NonNull
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        try {
            YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
            factoryBean.setResources(resource.getResource());

            Properties properties = factoryBean.getObject();

            // 如果 YAML 文件为空或解析失败，使用空属性
            if (properties == null) {
                properties = new Properties();
            }

            // 确定属性源名称
            String sourceName = determineSourceName(name, resource);

            return new PropertiesPropertySource(sourceName, properties);

        } catch (Exception e) {
            // 发生异常时返回空的属性源，而不是抛出异常
            String sourceName = determineSourceName(name, resource);
            return new PropertiesPropertySource(sourceName, new Properties());
        }
    }

    private String determineSourceName(String name, EncodedResource resource) {
        if (name != null) {
            return name;
        }
        if (resource.getResource().getFilename() != null) {
            return resource.getResource().getFilename();
        }
        return "unknownYamlSource";
    }
}
