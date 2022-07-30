package com.api.bookstore.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BooksDto {
    
    @NotBlank
    private String bookName;
    @NotBlank
    private String authorName;
    @NotBlank
    @Size(max = 10)
    private String bookGenre;
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getBookGenre() {
        return bookGenre;
    }
    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    
}
