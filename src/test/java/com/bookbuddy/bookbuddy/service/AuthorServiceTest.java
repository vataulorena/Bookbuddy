package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.author.AuthorResponse;
import com.bookbuddy.bookbuddy.dto.author.CreateAuthorRequest;
import com.bookbuddy.bookbuddy.entity.Author;
import com.bookbuddy.bookbuddy.entity.Book;
import com.bookbuddy.bookbuddy.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    private final AuthorRepository authorRepository = mock(AuthorRepository.class);
    private final AuthorService service = new AuthorService(authorRepository);

    @Test
    void create_shouldSaveAuthorWithName_andReturnEntity() {
        CreateAuthorRequest request = new CreateAuthorRequest("George Orwell");

        when(authorRepository.save(any())).thenAnswer(inv -> {
            Author a = inv.getArgument(0);
            a.setId(1L);
            return a;
        });

        Author saved = service.create(request);

        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        assertEquals("George Orwell", saved.getName());

        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).save(captor.capture());
        assertEquals("George Orwell", captor.getValue().getName());
    }

    @Test
    void list_shouldReturnAllAuthors_asResponses_withBookSummaries() {
        Author a = new Author();
        a.setId(1L);
        a.setName("George Orwell");

        Book b = new Book();
        b.setId(10L);
        b.setTitle("1984");
        b.setIsbn("9780451524935");

        a.setBooks(List.of(b));

        when(authorRepository.findAll()).thenReturn(List.of(a));

        List<AuthorResponse> result = service.list();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals("George Orwell", result.get(0).name());

        assertEquals(1, result.get(0).books().size());
        assertEquals(10L, result.get(0).books().get(0).id());
        assertEquals("1984", result.get(0).books().get(0).title());
        assertEquals("9780451524935", result.get(0).books().get(0).isbn());

        verify(authorRepository).findAll();
    }

    @Test
    void list_shouldReturnEmptyBooks_whenNull() {
        Author a = new Author();
        a.setId(1L);
        a.setName("George Orwell");
        a.setBooks(null);

        when(authorRepository.findAll()).thenReturn(List.of(a));

        List<AuthorResponse> result = service.list();

        assertEquals(1, result.size());
        assertNotNull(result.get(0).books());
        assertTrue(result.get(0).books().isEmpty());

        verify(authorRepository).findAll();
    }
}
