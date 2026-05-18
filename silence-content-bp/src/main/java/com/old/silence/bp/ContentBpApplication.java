package com.old.silence.bp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationEvent;
import com.old.silence.job.client.starter.EnableSilenceJob;

/**
 * @author moryzang
 */
@EnableFeignClients({"com.old.silence.content.api"})
@EnableSilenceJob
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ContentBpApplication {


    public static void main(String[] args) {
        // 方法1：设置系统属性，强制输出所有异常
        System.setProperty("java.awt.headless", "false");

        try {
            // 使用非静态方式启动
            SpringApplication app = new SpringApplication(ContentBpApplication.class);
            app.setAdditionalProfiles("CI-1");

            // 关键：注册一个启动失败的监听器
            app.addListeners((ApplicationEvent event) -> {
                if (event instanceof ApplicationFailedEvent) {
                    Throwable exception = ((ApplicationFailedEvent) event).getException();
                    System.err.println("========== STARTUP FAILURE ==========");
                    exception.printStackTrace(System.err);

                    // 打印所有 Cause Chain
                    Throwable cause = exception.getCause();
                    int level = 0;
                    while (cause != null && level < 10) {
                        System.err.println("\n--- Cause level " + (++level) + " ---");
                        cause.printStackTrace(System.err);
                        cause = cause.getCause();
                    }
                }
            });

            app.run(args);

        } catch (Throwable t) {
            System.err.println("========== FATAL ERROR ==========");
            t.printStackTrace(System.err);
            throw new RuntimeException(t);
        }
    }
}
