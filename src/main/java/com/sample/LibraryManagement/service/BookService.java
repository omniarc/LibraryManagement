package com.sample.LibraryManagement.service;


import com.sample.LibraryManagement.dto.request.BookAddRequestBody;
import com.sample.LibraryManagement.dto.request.BookUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;


public interface BookService {
    public BookAddResponseBody addBook(BookAddRequestBody bookAddRequestBody);
    public BookDeletionResponseBody deleteBook(String id);
    public BookUpdateResponseBody updateBook(BookUpdateRequestBody bookUpdateRequestBody);
    public BookFetchResponseBody getBook(String id);
    public BookPageResponseBody getBooksPaginated(int page, int size, String sortBy, String direction);

}