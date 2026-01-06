package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookListService {

    private final BookRepository bookRepository;

    public BookListService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<String> listTitles() {
        return bookRepository.findAll()
                .stream()
                .map(b -> b.getTitle())
                .toList();
    }
}
