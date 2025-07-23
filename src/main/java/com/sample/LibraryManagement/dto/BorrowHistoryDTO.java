package com.sample.LibraryManagement.dto;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BorrowHistoryDTO {
    private String id;
    private String borrowedDate;
    private String dueDate;
    private String returnDate;
    private String bookId;
    private String libraryMemberId;

}
