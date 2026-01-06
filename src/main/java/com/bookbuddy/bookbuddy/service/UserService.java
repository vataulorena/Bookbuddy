package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.user.CreateUserRequest;
import com.bookbuddy.bookbuddy.entity.User;
import com.bookbuddy.bookbuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(CreateUserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        return userRepository.save(user);
    }

    public List<User> list() {
        return userRepository.findAll();
    }
}
