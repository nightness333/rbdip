package org.kcheremnov.services.rabbit;

import org.kcheremnov.models.BalanceRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

    private static final String QUEUE_NAME = "balance-queue";

    private final RabbitTemplate rabbitTemplate;

    public MessageSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(BalanceRequest balanceRequest) {
        rabbitTemplate.convertAndSend(QUEUE_NAME, balanceRequest.toString());
    }
}
