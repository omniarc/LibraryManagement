package com.sample.LibraryManagement.service.implementation;


import com.sample.LibraryManagement.Entity.Book;
import com.sample.LibraryManagement.Entity.BorrowReturnHistory;
import com.sample.LibraryManagement.Entity.LibraryMember;
import com.sample.LibraryManagement.controller.BorrowReturnController;
import com.sample.LibraryManagement.dao.BookDao;
import com.sample.LibraryManagement.dao.BorrowReturnHistoryDao;
import com.sample.LibraryManagement.dao.LibraryMemberDao;
import com.sample.LibraryManagement.dto.request.BorrowRequestBody;
import com.sample.LibraryManagement.dto.request.ReturnRequestBody;
import com.sample.LibraryManagement.dto.response.BorrowResponseBody;
import com.sample.LibraryManagement.dto.response.ReturnResponseBody;
import com.sample.LibraryManagement.service.BorrowReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class BorrowReturnReturnServiceImpl implements BorrowReturnService {
    private static final Logger logger = LoggerFactory.getLogger(BorrowReturnController.class);
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BorrowReturnHistoryDao borrowReturnHistoryDao;
    @Autowired
    private LibraryMemberDao libraryMemberDao;


    @Cacheable(value = "books", key = "#id")
    public BorrowResponseBody borrowBook(BorrowRequestBody borrowRequestBody) {
        //Checking if said book is available for lending or not.
        Optional<Book> bookOptional = bookDao.findById(borrowRequestBody.getBookId());
        if(bookOptional.isPresent()) {
            Book book = bookOptional.get();
            if (book.getIsBorrowed() == true) {
                BorrowResponseBody borrowResponseObj = new BorrowResponseBody();
                borrowResponseObj.setMessage("Book is already issued to another user, and hence cannot be issued.");
                logger.info("Book is already lent to another user. Book ID : {}", borrowRequestBody.getBookId());
                return borrowResponseObj;
            }
            else {
                //Checking if user has the capacity to borrow more books.
                int booksCurrentlyBorrowed = borrowReturnHistoryDao.findByLibraryMemberIdAndIsActiveAndIsDeleted(borrowRequestBody.getLibraryMemberId(), true,false).size();
                if (booksCurrentlyBorrowed <= 4) {
                    BorrowReturnHistory newBorrow = new BorrowReturnHistory();
                    //Fetching timestamp of book lending.
                    LocalDateTime borrowingTimeStamp = LocalDateTime.now();
                    LocalDateTime dueDateStamp = borrowingTimeStamp.plusDays(14);
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
                    String formattedStamp = borrowingTimeStamp.format(dateTimeFormatter);
                    String formattedDueStamp = dueDateStamp.format(dateTimeFormatter);

                    //Changes to entities.
                    newBorrow.setBorrowedDate(formattedStamp);
                    newBorrow.setDueDate(formattedDueStamp);
                    Optional<LibraryMember> libraryMember = libraryMemberDao.findById(borrowRequestBody.getLibraryMemberId());
                    LibraryMember libMember = libraryMember.get();
                    newBorrow.setLibraryMember(libMember);
                    newBorrow.setBook(book);
                    //Changing base entity values to mark transaction status as active.
                    newBorrow.setIsActive(true);
                    newBorrow.setIsDeleted(false);
                    book.setIsBorrowed(true);
                    borrowReturnHistoryDao.save(newBorrow);
                    BorrowResponseBody borrowResponseBody = new BorrowResponseBody();
                    borrowResponseBody.setMessage("Book has been lent successfully. Return within two weeks!");
                    logger.info("Book lending request processed successfully.");
                    return borrowResponseBody;
                } else {
                    BorrowResponseBody borrowResponseBody = new BorrowResponseBody();
                    borrowResponseBody.setMessage("User has reached limit for issuing books.");
                    logger.info("Member reached lending limit.");
                    return borrowResponseBody;
                }
            }
        } else {
            BorrowResponseBody borrowResponseBody = new BorrowResponseBody();
            borrowResponseBody.setMessage("The given Book ID does not exist.");
            logger.info("Request exited, given book's ID does not exist.");
            return borrowResponseBody;
        }
    }




    @CacheEvict(value="books", key="#id")
    public ReturnResponseBody returnBook(ReturnRequestBody returnRequestBody) {

        Optional<BorrowReturnHistory> borrowReturnHistory = borrowReturnHistoryDao.findByLibraryMemberIdAndBookIdAndIsActiveAndIsDeleted(returnRequestBody.getLibraryMemberId(), returnRequestBody.getBookId(), true, false);
        if(borrowReturnHistory.isPresent()) {
            BorrowReturnHistory borrowReturnHistory1 = borrowReturnHistory.get();

            //Fetching timestamp of book being returned.
            LocalDateTime Stamp = LocalDateTime.now();
            DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
            String formattedStamp = Stamp.format(Formatter);

            //Background changes to return book.
            borrowReturnHistory1.setReturnDate(formattedStamp);
            borrowReturnHistory1.setIsActive(false);
            borrowReturnHistory1.setIsDeleted(true);
            Optional<Book> existingBookIdOptional = bookDao.findById(returnRequestBody.getBookId());
            Book book = existingBookIdOptional.get();
            book.setIsBorrowed(false);
            borrowReturnHistoryDao.save(borrowReturnHistory1);

            //Returning response
            ReturnResponseBody returnResponseBody = new ReturnResponseBody();
            returnResponseBody.setMessage("Book has been returned successfully.");
            logger.info("Request proccessed successfully, book returned successfully.");
            return returnResponseBody;
        }
        else{
            ReturnResponseBody returnResponseBody = new ReturnResponseBody();
            returnResponseBody.setMessage("Either the book Id, or the library member Id is invalid.");
            logger.info("Request processed, either the book's ID, or the member's ID is invalid.");
            return returnResponseBody;
        }
    }
}
