package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.author.CreateAuthorRequest;
import com.bookbuddy.bookbuddy.entity.Author;
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
    void create_shouldSaveAuthorWithName() {
        CreateAuthorRequest request = new CreateAuthorRequest("George Orwell");

        service.create(request);

        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).save(captor.capture());

        assertEquals("George Orwell", captor.getValue().getName());
    }

    @Test
    void list_shouldReturnAllAuthors() {
        Author a = new Author();
        a.setId(1L);
        a.setName("George Orwell");

        when(authorRepository.findAll()).thenReturn(List.of(a));

        List<Author> result = service.list();

        assertEquals(1, result.size());
        assertEquals("George Orwell", result.get(0).getName());
        verify(authorRepository).findAll();
    }
}
