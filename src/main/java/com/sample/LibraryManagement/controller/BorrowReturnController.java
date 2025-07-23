package com.sample.LibraryManagement.controller;

import com.sample.LibraryManagement.dto.request.BorrowRequestBody;
import com.sample.LibraryManagement.dto.request.ReturnRequestBody;
import com.sample.LibraryManagement.dto.response.BorrowResponseBody;
import com.sample.LibraryManagement.dto.response.ReturnResponseBody;
import com.sample.LibraryManagement.service.BorrowReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library-management")
public class BorrowReturnController {
    @Autowired
    BorrowReturnService borrowReturnService;

    @PostMapping("/lend")
    public BorrowResponseBody borrowBook(@RequestBody BorrowRequestBody borrowRequestBody){
        return borrowReturnService.borrowBook(borrowRequestBody);
    }

    @PutMapping("/return")
    public ReturnResponseBody returnBook(@RequestBody ReturnRequestBody returnRequestBody){
        return borrowReturnService.returnBook(returnRequestBody);
    }
}

