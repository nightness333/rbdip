package org.kcheremnov.services;

import org.kcheremnov.entities.Cart;
import org.kcheremnov.entities.Item;
import org.kcheremnov.entities.User;
import org.kcheremnov.repositories.CartRepository;
import org.kcheremnov.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;

    public PurchaseService(
            UserRepository userRepository,
            CartRepository cartRepository,
            CartService cartService
    ) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    public Long makePurchase(User user) {
        Long balance = user.getBalance();

        List<Item> items = user.getCart().getItems();

        Long sum = 0L;

        for (Item item : items) {
            sum += item.getPrice();
        }

        if (sum > balance) {
            throw new IllegalArgumentException("Not enough money");
        }

        user.setBalance(balance - sum);
        Cart cart = user.getCart();

        cart.setItems(List.of());

        userRepository.save(user);
        cartRepository.save(cart);

        return balance - sum;
    }
}
