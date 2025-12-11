package com.old.silence.content.code.generator.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceBuilder;

/**
 * @author moryzang
 */
@Configuration
public class DataSourceConfiguration {


    @Bean("contentDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.content")
    public DataSourceProperties contentDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("contentDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.content.druid") // 注意这里！
    public DataSource contentDataSource(@Qualifier("contentDataSourceProperties") DataSourceProperties properties) {
        // 使用 DruidDataSourceBuilder 替代
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setDriverClassName(properties.getDriverClassName());

        if (StringUtils.hasText(properties.getName())) {
            dataSource.setName(properties.getName());
        }
        return dataSource;
    }

    @Bean("contentJdbcTemplate")
    public JdbcTemplate getContentJdbcTemplate(@Qualifier("contentDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
