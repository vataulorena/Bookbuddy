package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.copy.BookCopyResponse;
import com.bookbuddy.bookbuddy.repository.BookCopyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyListService {

    private final BookCopyRepository bookCopyRepository;

    public CopyListService(BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    public List<BookCopyResponse> listByBook(Long bookId) {
        return bookCopyRepository.findByBookId(bookId)
                .stream()
                .map(c -> new BookCopyResponse(c.getId(), c.isAvailable(), bookId))
                .toList();
    }
}
