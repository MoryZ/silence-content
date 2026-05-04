package com.old.silence.content.infrastructure.mq.producer;


import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author moryzang
 */
@Service
@RocketMQMessageListener(
        topic = "Order-Topic",
        consumerGroup = "silence-content-consumer-group",
        maxReconsumeTimes = 1,
        selectorExpression = "*"  // 消费所有tag的消息
)
public class SilenceContentConsumer implements RocketMQListener<String> {


    private static final Logger log = LoggerFactory.getLogger(SilenceContentConsumer.class);

    @Override
    public void onMessage(String message) {
        log.error("Received message: {}", message);
        // 处理业务逻辑

        throw new RuntimeException("模拟消费失败，进入重试队列");
    }
}
