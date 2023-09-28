package com.example.photofiesta.service;

import com.example.photofiesta.exception.InformationExistException;
import com.example.photofiesta.models.User;
import com.example.photofiesta.repository.UserRepository;
import com.example.photofiesta.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public User createUser(User userObject) {
        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
            userObject.setPassword(passwordEncoder.encode((userObject.getPassword())));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("user email address " + userObject.getEmailAddress() + " already exists");
        }
    }

    //TODO complete loginUser method
//    public Optional<String> loginUser(LoginRequest loginRequest) {
//        UsernamePasswordAuthenticationToken
//    }

    public User findByUserEmailAddress(String emailAddress) {
        return userRepository.findUserByEmailAddress(emailAddress);
    }

}
