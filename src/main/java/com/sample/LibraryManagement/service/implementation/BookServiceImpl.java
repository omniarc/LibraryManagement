package com.sample.LibraryManagement.service.implementation;

import com.sample.LibraryManagement.Entity.Book;
import com.sample.LibraryManagement.controller.BookController;
import com.sample.LibraryManagement.dao.BookDao;
import com.sample.LibraryManagement.dto.BookDTO;
import com.sample.LibraryManagement.dto.request.BookAddRequestBody;
import com.sample.LibraryManagement.dto.request.BookUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;
import com.sample.LibraryManagement.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookDao bookDao;

    @Override
    public BookListResponseBody getAllBooks() {
        List<Book> allBooks = bookDao.findAll();
            return mapToBookDTO(allBooks);
    }


    private BookListResponseBody mapToBookDTO(List<Book> books) {
        List<BookDTO> bookDTOList = new ArrayList<>();

        for (Book book : books) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(book.getId());
            bookDTO.setTitle(book.getTitle());
            bookDTO.setAuthor(book.getAuthor());
            bookDTO.setGenre(book.getGenre());
            bookDTO.setPublishedYear(book.getPublishedYear().toString());
            bookDTO.setBorrowed(book.getIsBorrowed());

            bookDTOList.add(bookDTO);

        }
        BookListResponseBody bookList = new BookListResponseBody();
        bookList.setBooks(bookDTOList);
        logger.info("Successfully fetched list of all books.");
        return bookList;
    }

    public BookAddResponseBody addBook(BookAddRequestBody bookAddRequestBody) {
        Book newBook = mapToBook(bookAddRequestBody);
        bookDao.save(newBook);
        BookAddResponseBody bookAddResponseBody = new BookAddResponseBody();
        bookAddResponseBody.setMessage("Book added successfully.");
        return bookAddResponseBody;
    }

    private Book mapToBook(BookAddRequestBody bookAddRequestBody) {
        Book newbook = new Book();
        newbook.setTitle(bookAddRequestBody.getBook().getTitle());
        newbook.setAuthor(bookAddRequestBody.getBook().getAuthor());
        newbook.setGenre(bookAddRequestBody.getBook().getGenre());
        newbook.setPublishedYear(bookAddRequestBody.getBook().getPublishedYear());
        logger.info("Successfully added new book.");
        return newbook;
    }

    public BookDeletionResponseBody deleteBook(String id) {
        bookDao.deleteById(id);
        BookDeletionResponseBody bookDeletionResponseBody = new BookDeletionResponseBody();
        bookDeletionResponseBody.setMessage("Book deleted successfully.");
        logger.info("Book deleted with ID : {}", id);
        return bookDeletionResponseBody;
    }

    public BookUpdateResponseBody updateBook(BookUpdateRequestBody bookUpdateRequestBody) {
        // Book updBook = mapToBook(bookUpdateRequestBody);
        String id = bookUpdateRequestBody.getBookDetailsUpdate().getId();
        Optional<Book> existingBookOptional = bookDao.findById(id);
        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();
            existingBook.setTitle(bookUpdateRequestBody.getBookDetailsUpdate().getTitle());
            existingBook.setAuthor(bookUpdateRequestBody.getBookDetailsUpdate().getAuthor());
            existingBook.setGenre(bookUpdateRequestBody.getBookDetailsUpdate().getGenre());
            existingBook.setPublishedYear(bookUpdateRequestBody.getBookDetailsUpdate().getPublishedYear());
            bookDao.save(existingBook);

            BookUpdateResponseBody updateResponse = new BookUpdateResponseBody();
            updateResponse.setMessage("Book details updated successfully.");
            logger.info("Updated book details with ID : {}", bookUpdateRequestBody.getBookDetailsUpdate().getId());
            return updateResponse;
        } else {
            BookUpdateResponseBody failedUpdateResponse = new BookUpdateResponseBody();
            failedUpdateResponse.setMessage("The given ID does not exist.");
            logger.info("Update request processed with output : Given ID does not exist.");
            return failedUpdateResponse;
        }
    }

    public BookFetchResponseBody getBook(String id) {
        Optional<Book> existingBookOptional = bookDao.findById(id);
        if (existingBookOptional.isPresent()) {
            Book book = existingBookOptional.get();
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(book.getId());
            bookDTO.setTitle(book.getTitle());
            bookDTO.setAuthor(book.getAuthor());
            bookDTO.setPublishedYear(book.getPublishedYear());
            bookDTO.setGenre(book.getGenre());

            BookFetchResponseBody bookFetchResponseBody = new BookFetchResponseBody();
            bookFetchResponseBody.setBook(bookDTO);
            logger.info("Request to fetch a book's details processed successfully.");
            return bookFetchResponseBody;
        } else {
            BookFetchResponseBody failedFetch = new BookFetchResponseBody();
            failedFetch.setMessage("Fetch failed since the ID does not exist.");
            logger.info("Request to fetch a book exited, since ID does not exist.");
            return failedFetch;
        }
    }
}



