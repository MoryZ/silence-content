package com.old.silence.content.infrastructure.mq.producer;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


/**
 * @author moryzang
 */

@Component
public class SilenceContentProducer {

    private final MQProducer mqProducer;

    public SilenceContentProducer(MQProducer mqProducer) {
        this.mqProducer = mqProducer;
    }

    public void sendWithTagAndKey(String topic, String tag, String key, String content) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        var message = new Message(topic, tag, key, content.getBytes(StandardCharsets.UTF_8));
        mqProducer.send(message);
    }


}
