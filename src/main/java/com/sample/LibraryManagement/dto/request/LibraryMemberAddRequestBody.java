package com.sample.LibraryManagement.dto.request;

import com.sample.LibraryManagement.dto.LibraryMemberDTO;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LibraryMemberAddRequestBody {
    private LibraryMemberDTO user;
}
