package org.kcheremnov.services.rabbit;

import org.kcheremnov.models.BalanceRequest;
import org.kcheremnov.services.BalanceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiverService {

    private final BalanceService balanceService;

    public MessageReceiverService(
            BalanceService balanceService
    ) {
        this.balanceService = balanceService;
    }

    @RabbitListener(queues = "balance-queue")
    public void receiveMessage(String balanceRequest) {
        balanceService.balanceChange(BalanceRequest.fromString(balanceRequest));
    }
}
