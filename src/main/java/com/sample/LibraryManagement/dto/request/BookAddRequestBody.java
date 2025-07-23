package com.sample.LibraryManagement.dto.request;

import com.sample.LibraryManagement.dto.BookDTO;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookAddRequestBody {
    private BookDTO book;
}
