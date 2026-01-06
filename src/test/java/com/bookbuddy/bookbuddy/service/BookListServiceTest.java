package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.entity.Book;
import com.bookbuddy.bookbuddy.repository.BookRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookListServiceTest {

    private final BookRepository bookRepository = mock(BookRepository.class);
    private final BookListService service = new BookListService(bookRepository);

    @Test
    void listTitles_shouldReturnBookTitles() {
        Book b1 = new Book();
        b1.setTitle("1984");
        Book b2 = new Book();
        b2.setTitle("Harry Potter");

        when(bookRepository.findAll()).thenReturn(List.of(b1, b2));

        List<String> titles = service.listTitles();

        assertEquals(List.of("1984", "Harry Potter"), titles);
        verify(bookRepository).findAll();
    }
}
