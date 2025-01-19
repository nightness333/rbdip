package org.kcheremnov.controllers;

import org.kcheremnov.entities.User;
import org.kcheremnov.models.BalanceRequest;
import org.kcheremnov.services.UserService;
import org.kcheremnov.services.rabbit.MessageSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final MessageSenderService messageSenderService;
    private final UserService userService;

    public BalanceController(
            MessageSenderService messageSenderService,
            UserService userService
    ) {
        this.messageSenderService = messageSenderService;
        this.userService = userService;
    }

    @PutMapping("/replenish")
    public ResponseEntity<Long> replenishBalance(
            @RequestParam Long value
    ) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByLogin(login);

        messageSenderService.sendMessage(
                new BalanceRequest(value, user.getId())
        );

        return ResponseEntity.ok(user.getBalance() + value);
    }

    @PutMapping("/reduce")
    public ResponseEntity<Long> reduceBalance(
            @RequestParam Long value
    ) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByLogin(login);

        messageSenderService.sendMessage(
                new BalanceRequest(-value, user.getId())
        );

        return ResponseEntity.ok(user.getBalance() - value);
    }
}
