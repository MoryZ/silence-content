package com.old.silence.bp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import com.old.silence.job.client.starter.EnableSilenceJob;

/**
 * @author moryzang
 */
@EnableFeignClients({"com.old.silence.content.api"})
@EnableSilenceJob
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ContentBpApplication {


    public static void main(String[] args) {
        SpringApplication.run(ContentBpApplication.class, args);
    }
}
