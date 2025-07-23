package com.sample.LibraryManagement.dto.request;


import com.sample.LibraryManagement.dto.BaseEntityDTO;
import com.sample.LibraryManagement.dto.BookDTO;
import com.sample.LibraryManagement.dto.BorrowHistoryDTO;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnRequestBody {
    private String bookId;
    private String libraryMemberId;
}
