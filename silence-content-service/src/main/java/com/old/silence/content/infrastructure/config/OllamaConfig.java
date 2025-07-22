package com.old.silence.content.infrastructure.config;

import jakarta.annotation.Resource;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author moryzang
 */
@Configuration
public class OllamaConfig {

    @Resource
    private ChatModel ollamaChatModel;

    @Bean
    public ChatClient chatClient() {
        return ChatClient.builder(ollamaChatModel).build();
    }

}