package com.old.silence.bp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import com.old.silence.data.jdbc.autoconfigure.JdbcDataAutoConfiguration;

/**
 * @author MurrayZhang
 */

@SpringBootApplication(exclude = JdbcDataAutoConfiguration.class)
@EnableFeignClients({"com.old.silence.content.api"})
public class ContentBpApplication {


    public static void main(String[] args) {
        SpringApplication.run(ContentBpApplication.class, args);
    }
}
