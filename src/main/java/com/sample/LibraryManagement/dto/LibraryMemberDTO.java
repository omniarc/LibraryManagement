package com.sample.LibraryManagement.dto;

import com.sample.LibraryManagement.Entity.LibraryMember;
import com.sample.LibraryManagement.dto.request.LibraryMemberAddRequestBody;
import com.sample.LibraryManagement.dto.request.LibraryMemberUpdateRequestBody;
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

    public static LibraryMemberDTO fromLibraryMember(LibraryMember libraryMember){
        LibraryMemberDTO libraryMemberDTO = new LibraryMemberDTO();
        libraryMemberDTO.setId(libraryMember.getId());
        libraryMemberDTO.setName(libraryMember.getName());
        libraryMemberDTO.setContactNumber(libraryMember.getContactNumber());
        return libraryMemberDTO;
    }


    //Adding members to Library Member entity.
    public static LibraryMember toLibraryMember(LibraryMemberAddRequestBody libraryMemberAddRequestBody){
        LibraryMember libraryMember = new LibraryMember();
        libraryMember.setName(libraryMemberAddRequestBody.getUser().getName());
        libraryMember.setContactNumber(libraryMemberAddRequestBody.getUser().getContactNumber());
        return libraryMember;
    }

    public static void updateLibraryMemberRequest(LibraryMember libraryMember, LibraryMemberUpdateRequestBody libraryMemberUpdateRequestBody){
        libraryMember.setName(libraryMemberUpdateRequestBody.getUserDetailsUpdate().getName());
        libraryMember.setContactNumber(libraryMemberUpdateRequestBody.getUserDetailsUpdate().getContactNumber());
    }

}
