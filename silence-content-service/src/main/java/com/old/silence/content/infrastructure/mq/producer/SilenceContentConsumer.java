package com.old.silence.content.infrastructure.mq.producer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author MurrayZhang
 */
@Service
@RocketMQMessageListener(
        topic = "TEST_TOPIC2222",
        consumerGroup = "silence-content-consumer-group",
        selectorExpression = "*"  // 消费所有tag的消息
)
public class SilenceContentConsumer  implements RocketMQListener<String> {


    private static final Logger log = LoggerFactory.getLogger(SilenceContentConsumer.class);

    @Override
    public void onMessage(String message) {
        log.error("Received message: {}", message);
        // 处理业务逻辑
    }
}
