package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.copy.CreateBookCopyRequest;
import com.bookbuddy.bookbuddy.entity.Book;
import com.bookbuddy.bookbuddy.entity.BookCopy;
import com.bookbuddy.bookbuddy.repository.BookCopyRepository;
import com.bookbuddy.bookbuddy.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CopyCreateServiceTest {

    private final BookRepository bookRepository = mock(BookRepository.class);
    private final BookCopyRepository bookCopyRepository = mock(BookCopyRepository.class);

    private final CopyCreateService service = new CopyCreateService(bookRepository, bookCopyRepository);

    @Test
    void addCopy_shouldSaveCopyLinkedToBook() {
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        service.addCopy(1L, new CreateBookCopyRequest(true));

        ArgumentCaptor<BookCopy> captor = ArgumentCaptor.forClass(BookCopy.class);
        verify(bookCopyRepository).save(captor.capture());

        assertEquals(book, captor.getValue().getBook());
        assertTrue(captor.getValue().isAvailable());
    }
}
