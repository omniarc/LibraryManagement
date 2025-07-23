package com.sample.LibraryManagement.dto;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressDetailsDTO {
    private String id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
