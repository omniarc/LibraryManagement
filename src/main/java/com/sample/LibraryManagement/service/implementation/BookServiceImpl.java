package com.sample.LibraryManagement.service.implementation;

import com.sample.LibraryManagement.Entity.Book;
import com.sample.LibraryManagement.dao.BookDao;
import com.sample.LibraryManagement.dto.BookDTO;
import com.sample.LibraryManagement.dto.request.BookAddRequestBody;
import com.sample.LibraryManagement.dto.request.BookUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;
import com.sample.LibraryManagement.service.BookService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public BookListResponseBody getAllBooks() {
        List<Book> allBooks = bookDao.findAll();

        return mapToBookDTO(allBooks);
    }

    //EntityMapper - Interface to map from one class to another
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
        return newbook;
    }

    public BookDeletionResponseBody deleteBook(String id) {
        bookDao.deleteById(id);
        BookDeletionResponseBody bookDeletionResponseBody = new BookDeletionResponseBody();
        bookDeletionResponseBody.setMessage("Book deleted successfully.");
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
            return updateResponse;
        } else {
            BookUpdateResponseBody failedUpdateResponse = new BookUpdateResponseBody();
            failedUpdateResponse.setMessage("The given ID does not exist.");
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
            return bookFetchResponseBody;
        } else {
            BookFetchResponseBody failedFetch = new BookFetchResponseBody();
            failedFetch.setMessage("Fetch failed since the ID does not exist.");
            return failedFetch;
        }
    }
}



