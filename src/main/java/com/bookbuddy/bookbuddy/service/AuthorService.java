package com.bookbuddy.bookbuddy.service;

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

    public List<Author> list() {
        return authorRepository.findAll();
    }
}
