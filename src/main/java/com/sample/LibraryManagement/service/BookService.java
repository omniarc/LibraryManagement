package com.sample.LibraryManagement.service;


import com.sample.LibraryManagement.dto.request.BookAddRequestBody;
import com.sample.LibraryManagement.dto.request.BookUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;


public interface BookService {
    public BookListResponseBody getAllBooks();
    public BookAddResponseBody addBook(BookAddRequestBody bookAddRequestBody);
    public BookDeletionResponseBody deleteBook(String id);
    public BookUpdateResponseBody updateBook(BookUpdateRequestBody bookUpdateRequestBody);
    public BookFetchResponseBody getBook(String id);
    //public PaginatedResponseBody getPagedBooks(int page, int size);

}