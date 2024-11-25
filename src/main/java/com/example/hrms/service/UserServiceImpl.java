package com.example.hrms.service;

import com.example.hrms.entity.User;
import com.example.hrms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user from the repository by username
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Return a Spring Security User object with the roles mapped as authorities
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))  // Map RoleEnum to String
                        .collect(Collectors.toList()));
    }

    // Save user to database
    public User save(User user) {
        return userRepository.save(user);
    }
    public boolean usernameExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }



}
