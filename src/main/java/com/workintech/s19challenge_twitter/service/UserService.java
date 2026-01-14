package com.workintech.s19challenge_twitter.service;

import com.workintech.s19challenge_twitter.dto.LoginRequest;
import com.workintech.s19challenge_twitter.dto.RegisterRequest;
import com.workintech.s19challenge_twitter.entity.User;

public interface UserService {
    User registerUser(RegisterRequest registerRequest);
    String loginUser(LoginRequest loginRequest);
    User getCurrentUser();
    User findById(Long id);
    User findByUsername(String username);
    void deleteUser(Long id);
}
