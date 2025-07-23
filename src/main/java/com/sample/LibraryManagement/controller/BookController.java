package com.sample.LibraryManagement.controller;

import com.sample.LibraryManagement.dto.request.BookAddRequestBody;
import com.sample.LibraryManagement.dto.request.BookUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;
import com.sample.LibraryManagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/all")
    public BookListResponseBody getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping("/book")
    public BookAddResponseBody addBook(@RequestBody BookAddRequestBody bookAddRequestBody) {
        return bookService.addBook(bookAddRequestBody);
    }

    @DeleteMapping("/book/{id}")
    public BookDeletionResponseBody deleteBook(@PathVariable String id){
        return bookService.deleteBook(id);
    }

    @PostMapping("/book")
    public BookUpdateResponseBody updateBook(@RequestBody BookUpdateRequestBody bookUpdateRequestBody){
        return bookService.updateBook(bookUpdateRequestBody);
    }

    @GetMapping("/book/{id}")
    public BookFetchResponseBody getBook(@PathVariable String id){
        return bookService.getBook(id);
    }

}