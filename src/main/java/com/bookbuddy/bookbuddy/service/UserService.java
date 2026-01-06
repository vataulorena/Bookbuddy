package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.user.CreateUserRequest;
import com.bookbuddy.bookbuddy.dto.user.UserResponse;
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

    public List<UserResponse> listWithLoans() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private UserResponse toResponse(User user) {
        List<UserResponse.LoanSummary> loans = user.getLoans() == null
                ? List.of()
                : user.getLoans().stream()
                .map(l -> new UserResponse.LoanSummary(
                        l.getId(),
                        l.getBookCopyId(),
                        l.getStartDate() == null ? null : l.getStartDate().toString(),
                        l.getDueDate() == null ? null : l.getDueDate().toString(),
                        l.getReturnDate() == null ? null : l.getReturnDate().toString()
                ))
                .toList();

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                loans
        );
    }
}
