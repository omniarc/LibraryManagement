package com.sample.LibraryManagement.dto.response;


import com.sample.LibraryManagement.dto.LibraryMemberDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
//GET
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LibraryMemberListResponseBody {
    private List<LibraryMemberDTO> users;
}

