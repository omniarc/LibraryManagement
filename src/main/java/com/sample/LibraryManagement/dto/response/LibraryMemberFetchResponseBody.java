package com.sample.LibraryManagement.dto.response;


import com.sample.LibraryManagement.dto.AddressDetailsDTO;
import com.sample.LibraryManagement.dto.LibraryMemberDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
//GET
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LibraryMemberFetchResponseBody {
    private LibraryMemberDTO user;
    private AddressDetailsDTO address;
    private String message;
}
