package com.old.silence.content.api;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.old.silence.content.infrastructure.mq.producer.SilenceContentProducer;

import java.util.UUID;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class TestRocketMqController {

    private final SilenceContentProducer silenceContentProducer;
    public TestRocketMqController(SilenceContentProducer silenceContentProducer) {
        this.silenceContentProducer = silenceContentProducer;
    }

    @PostMapping("send")
    public void testRocketMq() throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        silenceContentProducer.sendWithTagAndKey("Order-Topic", "tag" , UUID.randomUUID().toString(),
                "Hello World:" + System.currentTimeMillis());
    }


}
