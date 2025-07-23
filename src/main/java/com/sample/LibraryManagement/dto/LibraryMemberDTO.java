package com.sample.LibraryManagement.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LibraryMemberDTO {
    private String id;
    private String name;
    private String contactNumber;
}
