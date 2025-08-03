package com.old.silence.content.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients({"com.old.silence.content.api"})
public class ContentConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentConsoleApplication.class, args);
    }
}
