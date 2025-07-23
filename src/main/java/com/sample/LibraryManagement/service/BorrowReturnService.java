package com.sample.LibraryManagement.service;

import com.sample.LibraryManagement.dto.request.ReturnRequestBody;
import com.sample.LibraryManagement.dto.response.BorrowResponseBody;
import com.sample.LibraryManagement.dto.request.BorrowRequestBody;
import com.sample.LibraryManagement.dto.response.ReturnResponseBody;

public interface BorrowReturnService {
    public BorrowResponseBody borrowBook(BorrowRequestBody borrowRequestBody);
    public ReturnResponseBody returnBook(ReturnRequestBody returnRequestBody);
}
