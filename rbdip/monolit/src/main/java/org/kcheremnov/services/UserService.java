package org.kcheremnov.services;

import org.kcheremnov.entities.Cart;
import org.kcheremnov.entities.User;
import org.kcheremnov.repositories.CartRepository;
import org.kcheremnov.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            CartRepository cartRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
    }

    public User getUserByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);

        if (!user.isPresent()) {
            throw new IllegalArgumentException("Can not found such user");
        }

        return user.get();
    }

    public boolean hasUser(String login) {
        Optional<User> user = userRepository.findByLogin(login);

        return user.isPresent();
    }

    public User saveUser(String login, String password) {
        User newUser = new User();

        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setLogin(login);

        Cart cart = new Cart();

        newUser.setCart(cart);

        return userRepository.save(newUser);
    }
}
