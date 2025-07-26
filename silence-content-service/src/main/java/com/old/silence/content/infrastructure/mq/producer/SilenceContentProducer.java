package com.old.silence.content.infrastructure.mq.producer;

/*
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

*/
/**
 * @author MurrayZhang
 *//*

@Component
public class SilenceContentProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String topic, String message) {
        rocketMQTemplate.convertAndSend(topic, message);
    }

    public void sendWithTag(String topic, String tag, String message) {
        rocketMQTemplate.convertAndSend(topic + ":" + tag, message);
    }

    public void sendOrderly(String topic, String message, String hashKey) {
        rocketMQTemplate.syncSendOrderly(topic, message, hashKey);
    }


}*/
