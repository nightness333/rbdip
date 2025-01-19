package org.kcheremnov.controllers;


import org.kcheremnov.entities.User;
import org.kcheremnov.models.BalanceResponse;
import org.kcheremnov.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByLogin(login);

        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setBalance(user.getBalance());

        return ResponseEntity.ok(balanceResponse);
    }
}