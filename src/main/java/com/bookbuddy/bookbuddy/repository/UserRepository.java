package com.bookbuddy.bookbuddy.repository;

import com.bookbuddy.bookbuddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
