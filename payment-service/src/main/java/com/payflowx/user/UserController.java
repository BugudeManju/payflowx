package com.payflowx.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;

    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {
        return userInfoService.getByEmail(authentication.getName());
    }
}