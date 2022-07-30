package com.api.bookstore.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.bookstore.dtos.BooksDto;
import com.api.bookstore.models.BooksModel;
import com.api.bookstore.services.BookStoreService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/books")
public class BookStoreController {
    
    final BookStoreService bookStoreService;

    public BookStoreController(BookStoreService bookStoreService) {
        this.bookStoreService = bookStoreService;
    }

    @PostMapping
    public ResponseEntity<Object> saveBook(@RequestBody @Valid BooksDto booksDto){

        if(bookStoreService.existsByBookName(booksDto.getBookName())){//Pesquisar Custom Validator (isolar responsabilidades)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito! Livro já registrado");
        }

        var booksModel = new BooksModel();
        BeanUtils.copyProperties(booksDto, booksModel);
        booksModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookStoreService.save(booksModel));
    }

    @GetMapping
    public ResponseEntity<Page<BooksModel>> getAllBooks(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(bookStoreService.findAll(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneBook(@PathVariable(value = "id") UUID id){
        Optional<BooksModel> booksModelOptional = bookStoreService.findById(id);
        if(!booksModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(booksModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "id") UUID id){
        Optional<BooksModel> booksModelOptional = bookStoreService.findById(id);
        if(!booksModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado!");
        }
        bookStoreService.delete(booksModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Livro deletado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "id") UUID id, @RequestBody @Valid BooksDto booksDto){
        Optional<BooksModel> booksModelOptional = bookStoreService.findById(id);
        if(!booksModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado!");
        }
        var booksModel = new BooksModel();
        BeanUtils.copyProperties(booksDto, booksModel);
        booksModel.setId(booksModelOptional.get().getId());
        booksModel.setRegistrationDate(booksModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(bookStoreService.save(booksModel));
    }

}
