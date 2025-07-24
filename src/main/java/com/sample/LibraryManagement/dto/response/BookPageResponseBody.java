package com.sample.LibraryManagement.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.LibraryManagement.dto.BookDTO;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookPageResponseBody {
    private List<BookDTO> data;
    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private boolean last;
}
