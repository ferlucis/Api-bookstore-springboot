package com.api.bookstore.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.bookstore.models.BooksModel;
import com.api.bookstore.repositories.BookStoreRepository;

@Service
public class BookStoreService {
    
    BookStoreRepository bookStoreRepository;

    public BookStoreService(BookStoreRepository bookStoreRepository) {
        this.bookStoreRepository = bookStoreRepository;
    }

    @Transactional
    public BooksModel save(BooksModel booksModel) {
        return bookStoreRepository.save(booksModel);
    }

    public boolean existsByBookName(String bookName) {
        return bookStoreRepository.existsByBookName(bookName);
    }

    public Page<BooksModel> findAll(Pageable pageable) {
        return bookStoreRepository.findAll(pageable);
    }

    public Optional<BooksModel> findById(UUID id) {
        return bookStoreRepository.findById(id);
    }

    @Transactional
    public void delete(BooksModel booksModel) {
        bookStoreRepository.delete(booksModel);
    }
}
