package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.book.BookResponse;
import com.bookbuddy.bookbuddy.dto.book.CreateBookRequest;
import com.bookbuddy.bookbuddy.entity.Author;
import com.bookbuddy.bookbuddy.entity.Book;
import com.bookbuddy.bookbuddy.entity.Category;
import com.bookbuddy.bookbuddy.repository.AuthorRepository;
import com.bookbuddy.bookbuddy.repository.BookRepository;
import com.bookbuddy.bookbuddy.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookCreateServiceTest {

    private final BookRepository bookRepository = mock(BookRepository.class);
    private final AuthorRepository authorRepository = mock(AuthorRepository.class);
    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);

    private final BookCreateService service =
            new BookCreateService(bookRepository, authorRepository, categoryRepository);

    @Test
    void create_shouldSaveBookWithAuthorAndCategory_andReturnResponse() {
        Author author = new Author();
        author.setId(1L);

        Category category = new Category();
        category.setId(2L);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));

        when(bookRepository.save(any())).thenAnswer(inv -> {
            Book b = inv.getArgument(0);
            b.setId(10L);
            return b;
        });

        CreateBookRequest req = new CreateBookRequest(
                "1984", "9780451524935", "Roman distopic", 1L, 2L
        );

        BookResponse resp = service.create(req);

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());

        Book saved = captor.getValue();
        assertEquals("1984", saved.getTitle());
        assertEquals("9780451524935", saved.getIsbn());
        assertEquals("Roman distopic", saved.getDescription());
        assertEquals(author, saved.getAuthor());
        assertEquals(category, saved.getCategory());

        assertEquals(10L, resp.id());
        assertEquals("1984", resp.title());
    }

    @Test
    void create_shouldThrow_whenAuthorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        CreateBookRequest req = new CreateBookRequest(
                "1984", "9780451524935", "Roman distopic", 1L, 2L
        );

        BusinessException ex = assertThrows(BusinessException.class, () -> service.create(req));
        assertEquals("Autor inexistent", ex.getMessage());

        verify(bookRepository, never()).save(any());
    }

    @Test
    void create_shouldThrow_whenCategoryNotFound() {
        Author author = new Author();
        author.setId(1L);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        CreateBookRequest req = new CreateBookRequest(
                "1984", "9780451524935", "Roman distopic", 1L, 2L
        );

        BusinessException ex = assertThrows(BusinessException.class, () -> service.create(req));
        assertEquals("Categorie inexistenta", ex.getMessage());

        verify(bookRepository, never()).save(any());
    }
}
