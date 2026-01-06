package com.bookbuddy.bookbuddy.repository;

import com.bookbuddy.bookbuddy.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
