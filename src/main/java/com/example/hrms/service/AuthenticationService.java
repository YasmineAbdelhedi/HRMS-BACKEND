package com.example.hrms.service;
import com.example.hrms.dto.LoginUserDto;
import com.example.hrms.dto.RegisterUserDto;
import com.example.hrms.repository.RoleRepository;
import com.example.hrms.entity.Role;
import com.example.hrms.entity.User;
import com.example.hrms.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            RoleRepository roleRepository,  // Inject RoleRepository
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;

    }

    public User signup(RegisterUserDto input) {
        // Create a new User object
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        // Convert RoleEnum to Role entity and set roles
        Set<Role> roles = input.getRoles().stream()
                .map(roleEnum -> roleRepository.findByName(roleEnum)
                        .orElseThrow(() -> new RuntimeException("Role not found"))) // Fetch Role entity from the database
                .collect(Collectors.toSet());

        user.setRoles(roles);

        // Save the user with roles
        return userRepository.save(user);
    }


    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
