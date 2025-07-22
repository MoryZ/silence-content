package com.old.silence.content.api;

import jakarta.annotation.Resource;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.infrastructure.config.MyMarkdownReader;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class RagResource implements CommandLineRunner {

    @Resource
    private ChatClient chatClient;

    @Resource
    private ElasticsearchVectorStore vectorStore;

    @Resource
    private MyMarkdownReader myMarkdownReader;

    @Override
    public void run(String... args) {
        List<Document> documents = myMarkdownReader.loadMarkdown();
        vectorStore.add(documents);
        System.out.println("文档写入 ES 成功");
    }

    @GetMapping("/rags/completion")
    public String completion(@RequestParam String question) {
        return chatClient.prompt()
                .user(question)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .call()
                .content();
    }
}
