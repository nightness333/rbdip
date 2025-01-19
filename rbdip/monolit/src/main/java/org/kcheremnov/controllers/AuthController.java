package org.kcheremnov.controllers;

import org.kcheremnov.models.AuthRequest;
import org.kcheremnov.models.AuthResponse;
import org.kcheremnov.security.JwtTokenUtil;
import org.kcheremnov.security.UserDetailsServiceImpl;
import org.kcheremnov.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(
            JwtTokenUtil jwtTokenUtil,
            UserDetailsServiceImpl userDetailsService,
            AuthenticationManager authenticationManager,
            UserService userService
    ) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest authenticationRequest) throws Exception {
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticationRequest.getLogin(),
                                authenticationRequest.getPassword()
                        )
                );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getLogin());
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody AuthRequest user) throws Exception {
        if (userService.hasUser(user.getLogin())) {
            return ResponseEntity.badRequest().build();
        }

        userService.saveUser(user.getLogin(), user.getPassword());

        return ResponseEntity.ok("User registered successfully!");
    }
}