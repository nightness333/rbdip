package org.kcheremnov.services;

import org.kcheremnov.entities.User;
import org.kcheremnov.models.BalanceRequest;
import org.kcheremnov.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceService {

    private final UserRepository userRepository;

    public BalanceService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public void balanceChange(BalanceRequest balanceRequest) {
        Optional<User> optionalUser = userRepository.findById(balanceRequest.getUserId());

        if (!optionalUser.isPresent()) {
            return;
        }

        User user = optionalUser.get();
        Long newBalance = user.getBalance() + balanceRequest.getChange();
        user.setBalance(newBalance);

        userRepository.save(user);
    }
}
