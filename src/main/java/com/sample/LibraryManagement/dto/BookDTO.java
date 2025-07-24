package com.sample.LibraryManagement.dto;
import com.sample.LibraryManagement.Entity.Book;
import com.sample.LibraryManagement.dto.request.BookAddRequestBody;
import com.sample.LibraryManagement.dto.request.BookUpdateRequestBody;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {
    private String id;
    private String title;
    private String author;
    private String genre;
    private String publishedYear;
    private boolean isBorrowed;

    //Fetching paginated list of pre-existing books.
    public static BookDTO fromBook(Book book){

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        book.setAuthor(book.getAuthor());
        book.setGenre(book.getGenre());
        book.setPublishedYear(book.getPublishedYear());
        bookDTO.setBorrowed(book.getIsBorrowed());
        return bookDTO;
    }

    //Adding members to Book entity.
    public static Book toBook(BookAddRequestBody bookAddRequestBody){
        Book newbook = new Book();
        newbook.setTitle(bookAddRequestBody.getBook().getTitle());
        newbook.setAuthor(bookAddRequestBody.getBook().getAuthor());
        newbook.setGenre(bookAddRequestBody.getBook().getGenre());
        newbook.setPublishedYear(bookAddRequestBody.getBook().getPublishedYear());
        return newbook;
    }

    public static void updateBookRequest(Book book, BookUpdateRequestBody bookUpdateRequestBody){
        book.setTitle(bookUpdateRequestBody.getBookDetailsUpdate().getTitle());
        book.setAuthor(bookUpdateRequestBody.getBookDetailsUpdate().getAuthor());
        book.setGenre(bookUpdateRequestBody.getBookDetailsUpdate().getGenre());
        book.setPublishedYear((bookUpdateRequestBody.getBookDetailsUpdate().getPublishedYear()));
    }

}