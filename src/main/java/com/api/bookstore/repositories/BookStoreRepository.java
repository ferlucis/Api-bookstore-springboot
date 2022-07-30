package com.api.bookstore.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.bookstore.models.BooksModel;

@Repository
public interface BookStoreRepository extends JpaRepository<BooksModel, UUID>{
    
    boolean existsByBookName(String bookName);

}
