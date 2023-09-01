package com.shiyuan.bookstore.service;

import java.util.HashMap;
import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiyuan.bookstore.entity.Authority;
import com.shiyuan.bookstore.entity.User;
import com.shiyuan.bookstore.repository.AuthorityRepository;
import com.shiyuan.bookstore.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    // @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public User getUser(String username) {
        return userRepository.findById(username).orElse(null);
    }

    @Transactional
    public Map<String, Object> registerCustomer(User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Check if the username already exists
            if (userRepository.existsById(user.getUsername())) {
                response.put("success", false);
                response.put("message", "Failed to register user: Username already exists.");
            } else {
                // Insert the new user into the users table
                userRepository.save(user);

                // Create a new Authority object with CUSTOMER role and save it
                Authority authority = new Authority(user.getUsername(), "ROLE_CUSTOMER");
                authorityRepository.save(authority);
                response.put("success", true);
                response.put("message", "User successfully registered.");
            }

        } catch (Exception e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : "Unknown error";
            response.put("success", false);
            response.put("message", "Failed to register user: " + errorMessage);
        }
        return response;
    }

    public void saveMyUser(User user) {
        userRepository.save(user);

    }
}
