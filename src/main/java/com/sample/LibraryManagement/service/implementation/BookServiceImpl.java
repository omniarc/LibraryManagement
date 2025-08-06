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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

//    @Autowired
//    private BookDao bookDao;          THIS IS OLD, AND A BAD PRACTICE. #FIELD INJECTION

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao){
        this.bookDao = bookDao;                         //THIS IS NEW AND RELATIVELY DOPE-R. #CONSTRUCTOR INJECTION
    }


    public BookAddResponseBody addBook(BookAddRequestBody bookAddRequestBody) {
        Book newBook = BookDTO.toBook(bookAddRequestBody);
        bookDao.save(newBook);
        BookAddResponseBody bookAddResponseBody = new BookAddResponseBody();
        bookAddResponseBody.setMessage("Book added successfully.");
        return bookAddResponseBody;
    }

    @CacheEvict(value="books", key="#id")
    public BookDeletionResponseBody deleteBook(String id) {
        bookDao.deleteById(id);
        BookDeletionResponseBody bookDeletionResponseBody = new BookDeletionResponseBody();
        bookDeletionResponseBody.setMessage("Book deleted successfully.");
        logger.info("Book deleted with ID : {}", id);
        return bookDeletionResponseBody;
    }

    //@CachePut(value="books", key="#id")
    public BookUpdateResponseBody updateBook(BookUpdateRequestBody bookUpdateRequestBody) {
        String id = bookUpdateRequestBody.getBookDetailsUpdate().getId();
        Optional<Book> existingBookOptional = bookDao.findById(id);
        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();
            BookDTO.updateBookRequest(existingBook, bookUpdateRequestBody);
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

    @Cacheable(value = "books", key = "#id")
    public BookFetchResponseBody getBook(String id) {
        Optional<Book> existingBookOptional = bookDao.findById(id);
        if (existingBookOptional.isPresent()) {
            Book book = existingBookOptional.get();
            BookDTO bookDTO = BookDTO.fromBook(book);
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

    @Override
    public BookPageResponseBody getBooksPaginated(int page, int size, String sortBy, String direction){
        Sort sortBook = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sortBook);

        Page<Book> pagedResult = bookDao.findAll(pageable);

        List<BookDTO> bookDTOList = pagedResult.getContent().stream()
                .map(BookDTO::fromBook)
                .toList();

        BookPageResponseBody bookPageResponseBody = new BookPageResponseBody();
        bookPageResponseBody.setData(bookDTOList);
        bookPageResponseBody.setPageNumber(pagedResult.getNumber());
        bookPageResponseBody.setPageSize(pagedResult.getSize());
        bookPageResponseBody.setTotalElements(pagedResult.getTotalElements());
        bookPageResponseBody.setTotalPages(pagedResult.getTotalPages());
        bookPageResponseBody.setLast(pagedResult.isLast());

        logger.info("Paginated book fetch: page {} of {}", page, pagedResult.getTotalPages());
        return bookPageResponseBody;
    }
}



