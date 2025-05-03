package com.old.silence.content.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.domain.model.Account;
import com.old.silence.content.infrastructure.mq.producer.SilenceContentProducer;
import com.old.silence.content.infrastructure.persistence.dao.AccountDao;
import com.old.silence.content.util.FileReadUtils;
import com.old.silence.json.JacksonMapper;

/**
 * @author MurrayZhang
 */
@RequestMapping("/api/v1")
@RestController
public class TestRocketMqResource {

    private final SilenceContentProducer silenceContentProducer;

    public TestRocketMqResource(SilenceContentProducer silenceContentProducer) {
        this.silenceContentProducer = silenceContentProducer;
    }

    @GetMapping("/mq/test")
    public void bulk() {
        silenceContentProducer.sendMessage("TEST_TOPIC2222", "test" + System.currentTimeMillis());
    }
}
