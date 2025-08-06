package com.sample.LibraryManagement.controller;

import com.sample.LibraryManagement.dto.request.BookAddRequestBody;
import com.sample.LibraryManagement.dto.request.BookUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;
import com.sample.LibraryManagement.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);


    private final BookService bookService;
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public BookPageResponseBody getAllBooks(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size,
                                            @RequestParam(defaultValue = "title") String sortBy,
                                            @RequestParam(defaultValue = "asc") String direction) {
        logger.info("Request received to fetch paginated list of books : page={}, size={}, sortBy={}, direction={}",page, size, sortBy, direction);
        return bookService.getBooksPaginated(page, size, sortBy, direction);
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