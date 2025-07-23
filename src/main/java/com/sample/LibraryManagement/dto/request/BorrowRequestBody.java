package com.sample.LibraryManagement.dto.request;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequestBody {
    private String bookId;
    private String libraryMemberId;
}
