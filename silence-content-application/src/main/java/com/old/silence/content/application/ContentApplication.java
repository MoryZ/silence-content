package com.old.silence.content.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author moryzang
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients({"com.old.silence.content.api", "com.old.silence.content.application.api"})
public class ContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
    }
}
