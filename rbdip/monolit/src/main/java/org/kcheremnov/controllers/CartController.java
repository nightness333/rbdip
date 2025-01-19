package org.kcheremnov.controllers;

import org.kcheremnov.entities.Cart;
import org.kcheremnov.entities.Item;
import org.kcheremnov.entities.User;
import org.kcheremnov.services.CartService;
import org.kcheremnov.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(
            CartService cartService,
            UserService userService
    ) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Item> addItemToCart(
            @RequestParam Long itemId
    ) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByLogin(login);

        Item item = cartService.addItemToCart(itemId, user);

        return ResponseEntity.ok(item);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteItemFromCart(
            @RequestParam Long itemId
    ) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByLogin(login);

        cartService.deleteItemFromCart(itemId, user);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Item>> getCart() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByLogin(login);

        List<Item> items = user.getCart().getItems();

        return ResponseEntity.ok(items);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
