package com.sample.LibraryManagement.controller;

import com.sample.LibraryManagement.dto.request.BorrowRequestBody;
import com.sample.LibraryManagement.dto.request.ReturnRequestBody;
import com.sample.LibraryManagement.dto.response.BorrowResponseBody;
import com.sample.LibraryManagement.dto.response.ReturnResponseBody;
import com.sample.LibraryManagement.service.BorrowReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library-management")
public class BorrowReturnController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowReturnController.class);

    @Autowired
    BorrowReturnService borrowReturnService;

    @PostMapping("/borrow")
    public BorrowResponseBody borrowBook(@RequestBody BorrowRequestBody borrowRequestBody){
        logger.info("Request received to lend book with ID : {} to user with ID : {}", borrowRequestBody.getBookId(), borrowRequestBody.getLibraryMemberId());
        return borrowReturnService.borrowBook(borrowRequestBody);
    }

    @PutMapping("/return")
    public ReturnResponseBody returnBook(@RequestBody ReturnRequestBody returnRequestBody){
        logger.info("Request received to return book with ID : {} by user with ID : {}", returnRequestBody.getBookId(), returnRequestBody.getLibraryMemberId());
        return borrowReturnService.returnBook(returnRequestBody);
    }
}

