package com.old.silence.content.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author MurrayZhang
 * @Description
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(
        {"com.old.silence.content.api"}
)
public class ContentConsoleApplication {


    public static void main(String[] args) {
        SpringApplication.run(ContentConsoleApplication.class, args);
    }
}
