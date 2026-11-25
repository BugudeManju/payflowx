package com.payflowx.user;

import com.payflowx.auth.RegisterRequest;
import com.payflowx.userinfodetails.UserInfoDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService {

    private static final String DEFAULT_ROLE = "MERCHANT";

    private final UserRepo repository;
    private final PasswordEncoder encoder;

    public UserInfoService(UserRepo repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    // Spring Security calls this to find a user by the "username" (our email)
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = repository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + username));

        return new UserInfoDetails(user);
    }

    public void register(RegisterRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Everyone signs up as a merchant; admins are promoted directly in the database
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .roles(DEFAULT_ROLE)
                .build();
        repository.save(user);
    }
}
