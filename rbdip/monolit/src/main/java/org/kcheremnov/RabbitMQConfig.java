package org.kcheremnov;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "balance-queue";

    @Bean
    public Queue balanceQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}
