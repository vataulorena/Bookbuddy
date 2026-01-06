package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.book.CreateBookRequest;
import com.bookbuddy.bookbuddy.entity.Author;
import com.bookbuddy.bookbuddy.entity.Book;
import com.bookbuddy.bookbuddy.entity.Category;
import com.bookbuddy.bookbuddy.repository.AuthorRepository;
import com.bookbuddy.bookbuddy.repository.BookRepository;
import com.bookbuddy.bookbuddy.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class BookCreateService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookCreateService(BookRepository bookRepository,
                             AuthorRepository authorRepository,
                             CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    public void create(CreateBookRequest request) {
        Author author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new BusinessException("Autor inexistent"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new BusinessException("Categorie inexistenta"));

        Book book = new Book();
        book.setTitle(request.title());
        book.setIsbn(request.isbn());
        book.setDescription(request.description());
        book.setAuthor(author);
        book.setCategory(category);

        bookRepository.save(book);
    }
}
