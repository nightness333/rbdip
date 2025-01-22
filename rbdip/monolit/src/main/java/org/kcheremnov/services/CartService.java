package org.kcheremnov.services;

import org.kcheremnov.entities.Cart;
import org.kcheremnov.entities.Item;
import org.kcheremnov.entities.User;
import org.kcheremnov.repositories.CartRepository;
import org.kcheremnov.repositories.ItemsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    CartRepository cartRepository;
    ItemsRepository itemsRepository;

    public CartService(
            CartRepository cartRepository,
            ItemsRepository itemsRepository
    ) {
        this.cartRepository = cartRepository;
        this.itemsRepository = itemsRepository;
    }

    public Item addItemToCart(Long itemId, User user) {
        Item item;

        try {
            item = itemsRepository.findById(itemId).get();
        } catch (Exception ex) {
            throw new IllegalArgumentException("No such item");
        }

        Cart cart = user.getCart();
        List<Item> items = cart.getItems();

        if (items.contains(item)) {
            throw new IllegalArgumentException("Cart has item already");
        }

        items.add(item);
        cart.setItems(items);

        cartRepository.save(cart);

        return item;
    }

    public void deleteItemFromCart(Long itemId, User user) {
        Item item;

        try {
            item = itemsRepository.getReferenceById(itemId);
        } catch (Exception ex) {
            throw new IllegalArgumentException("No such item");
        }

        Cart cart = user.getCart();
        List<Item> items = cart.getItems();

        if (!items.contains(item)) {
            throw new IllegalArgumentException("No such item in cart");
        }

        items.remove(item);
        cart.setItems(items);

        cartRepository.save(cart);
    }
}
