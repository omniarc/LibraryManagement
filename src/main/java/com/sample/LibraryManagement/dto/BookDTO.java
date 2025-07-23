package com.sample.LibraryManagement.dto;
import lombok.*;

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

//    public BookDTO(String id, String title, String author, String genre, String publishedYear, boolean isBorrowed){
//        this.id = id;
//        this.title = title;
//        this.author = author;
//        this.genre = genre;
//        this.publishedYear = publishedYear;
//        this.isBorrowed = isBorrowed;
//    }
}
// fetch first