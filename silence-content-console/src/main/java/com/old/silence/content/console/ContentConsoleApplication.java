package com.old.silence.content.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Console应用启动类
 * 
 * 通过@ComponentScan扫描code-generator模块的包，
 * 使其所有@RestController、@Service等注解的Bean都可以直接使用
 * 
 * 这样code-generator的GeneratorResource就可以直接在console中使用，
 * 无需Feign，也不需要重复创建Controller
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients({"com.old.silence.content.api"})
@ComponentScan(basePackages = {
    "com.old.silence.content.console",           // console自己的包
    "com.old.silence.content.code.generator"     // code-generator模块的包
})
public class ContentConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentConsoleApplication.class, args);
    }
}
