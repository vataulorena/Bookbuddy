package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.author.AuthorResponse;
import com.bookbuddy.bookbuddy.dto.author.CreateAuthorRequest;
import com.bookbuddy.bookbuddy.entity.Author;
import com.bookbuddy.bookbuddy.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author create(CreateAuthorRequest request) {
        Author author = new Author();
        author.setName(request.name());
        return authorRepository.save(author);
    }

    public List<AuthorResponse> list() {
        return authorRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AuthorResponse toResponse(Author author) {
        List<AuthorResponse.BookSummary> books = author.getBooks() == null
                ? List.of()
                : author.getBooks().stream()
                .map(b -> new AuthorResponse.BookSummary(b.getId(), b.getTitle(), b.getIsbn()))
                .toList();

        return new AuthorResponse(author.getId(), author.getName(), books);
    }
}
