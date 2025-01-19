package org.kcheremnov.controllers;

import org.kcheremnov.entities.User;
import org.kcheremnov.services.PurchaseService;
import org.kcheremnov.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final UserService userService;

    public PurchaseController(
            PurchaseService purchaseService,
            UserService userService
    ) {
        this.purchaseService = purchaseService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Long> makePurchase() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByLogin(login);

        Long newBalance = purchaseService.makePurchase(user);

        return ResponseEntity.ok(newBalance);
    }
}
