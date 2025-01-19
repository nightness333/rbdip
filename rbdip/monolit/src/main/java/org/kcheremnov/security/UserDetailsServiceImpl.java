package org.kcheremnov.security;

import org.kcheremnov.entities.User;
import org.kcheremnov.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByLogin(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        User appUser = user.get();

        return new org.springframework.security.core.userdetails.User(appUser.getLogin(), appUser.getPassword(), List.of());
    }
}
