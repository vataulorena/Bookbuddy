package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.copy.BookCopyResponse;
import com.bookbuddy.bookbuddy.dto.copy.CreateBookCopyRequest;
import com.bookbuddy.bookbuddy.entity.Book;
import com.bookbuddy.bookbuddy.entity.BookCopy;
import com.bookbuddy.bookbuddy.repository.BookCopyRepository;
import com.bookbuddy.bookbuddy.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class CopyCreateService {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;

    public CopyCreateService(BookRepository bookRepository,
                             BookCopyRepository bookCopyRepository) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    public BookCopyResponse addCopy(Long bookId, CreateBookCopyRequest request) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BusinessException("Carte inexistenta"));

        BookCopy copy = new BookCopy();
        copy.setBook(book);
        copy.setAvailable(Boolean.TRUE.equals(request.available()));

        BookCopy saved = bookCopyRepository.save(copy);

        return new BookCopyResponse(saved.getId(), saved.isAvailable(), bookId);
    }
}
