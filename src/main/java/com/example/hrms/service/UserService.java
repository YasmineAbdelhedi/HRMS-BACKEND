package com.example.hrms.service;

import com.example.hrms.entity.User;
import com.example.hrms.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}