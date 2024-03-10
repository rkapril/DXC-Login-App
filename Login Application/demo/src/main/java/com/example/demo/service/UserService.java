package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @PostConstruct
    public void init() {
        // Creating an initial user
        User initialUser1 = new User("admin", "1234", "John", "manager");
        userRepository.save(initialUser1);
        System.out.println("User initialized: " + initialUser1.getUsername());

        // Creating another user
        User initialUser2 = new User("user", "1234", "Jane", "employee");
        userRepository.save(initialUser2);
        System.out.println("User initialized: " + initialUser2.getUsername());
    }

    public User findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    public User login(String username, String password) {
        System.out.println("Login attempt - Username: " + username + ", Password: " + password);
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            System.out.println("Login successful for user: " + user.getUsername());
        } else {
            System.out.println("Login failed for user: " + username);
        }
        return user;
    }


    // Other service methods...
}
