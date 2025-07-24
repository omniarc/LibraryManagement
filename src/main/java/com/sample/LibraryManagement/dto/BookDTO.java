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
}