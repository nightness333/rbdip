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

    public PurchaseService(
            UserRepository userRepository,
            CartRepository cartRepository
    ) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public Long makePurchase(User user) {
        Long balance = user.getBalance();

        Cart cart = user.getCart();
        List<Item> items = cart.getItems();

        Long sum = 0L;

        for (Item item : items) {
            sum += item.getPrice();
        }

        if (sum > balance) {
            throw new IllegalArgumentException("Not enough money");
        }

        items.clear();
        cart.setItems(items);

        user.setBalance(balance - sum);
        userRepository.save(user);

        cartRepository.save(cart);

        return balance - sum;
    }
}
