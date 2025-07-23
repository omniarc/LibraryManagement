package com.sample.LibraryManagement.controller;

import com.sample.LibraryManagement.dto.request.BookAddRequestBody;
import com.sample.LibraryManagement.dto.request.BookUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;
import com.sample.LibraryManagement.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);


    @Autowired
    BookService bookService;

    @GetMapping("/all")
    public BookListResponseBody getAllBooks() {
        logger.info("Request received to fetch the list of all books.");
        return bookService.getAllBooks();
    }

    @PutMapping("/book")
    public BookAddResponseBody addBook(@RequestBody BookAddRequestBody bookAddRequestBody) {
        logger.info("Request received to add a new book.");
        return bookService.addBook(bookAddRequestBody);
    }

    @DeleteMapping("/book/{id}")
    public BookDeletionResponseBody deleteBook(@PathVariable String id){
        logger.info("Request received to delete the book with ID : {}", id);
        return bookService.deleteBook(id);
    }

    @PostMapping("/book")
    public BookUpdateResponseBody updateBook(@RequestBody BookUpdateRequestBody bookUpdateRequestBody){
        logger.info("Request received to update details of book with ID : {}", bookUpdateRequestBody.getBookDetailsUpdate().getId());
        return bookService.updateBook(bookUpdateRequestBody);
    }

    @GetMapping("/book/{id}")
    public BookFetchResponseBody getBook(@PathVariable String id){
        logger.info("Request received to fetch the details of book with ID : {}", id);
        return bookService.getBook(id);
    }

}