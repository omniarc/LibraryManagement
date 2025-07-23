package com.sample.LibraryManagement.service.implementation;

import com.sample.LibraryManagement.Entity.LibraryMember;
import com.sample.LibraryManagement.dao.LibraryMemberDao;
import com.sample.LibraryManagement.dto.LibraryMemberDTO;
import com.sample.LibraryManagement.dto.request.LibraryMemberAddRequestBody;
import com.sample.LibraryManagement.dto.request.LibraryMemberUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;
import com.sample.LibraryManagement.service.LibraryMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    @Autowired
    private LibraryMemberDao libraryMemberDao;

    @Override
    public LibraryMemberListResponseBody getAllUsers() {
        List<LibraryMember> allMembers = libraryMemberDao.findAll();
        return mapToLibraryMemberDTO(allMembers);
    }

    private LibraryMemberListResponseBody mapToLibraryMemberDTO(List<LibraryMember> members) {
        List<LibraryMemberDTO> memberDTOList = new ArrayList<>();

        for (LibraryMember member : members) {
            LibraryMemberDTO libraryMemberDTO = new LibraryMemberDTO();
            libraryMemberDTO.setId(member.getId());
            libraryMemberDTO.setName(member.getName());
            libraryMemberDTO.setContactNumber(member.getContactNumber());

            memberDTOList.add(libraryMemberDTO);

        }
        LibraryMemberListResponseBody userList = new LibraryMemberListResponseBody();
        userList.setUsers(memberDTOList);
        return userList;
    }

    @Override
    public LibraryMemberAddResponseBody addMember(LibraryMemberAddRequestBody libraryMemberAddRequestBody){
        LibraryMember newMember = mapToLibraryMember(libraryMemberAddRequestBody);
        libraryMemberDao.save(newMember);
        LibraryMemberAddResponseBody libraryMemberAddResponseBody = new LibraryMemberAddResponseBody();
        libraryMemberAddResponseBody.setMessage("User added successfully.");
        return libraryMemberAddResponseBody;
    }


    private LibraryMember mapToLibraryMember(LibraryMemberAddRequestBody libraryMemberAddRequestBody){
        LibraryMember newMember = new LibraryMember();
        newMember.setName(libraryMemberAddRequestBody.getUser().getName());
        newMember.setContactNumber(libraryMemberAddRequestBody.getUser().getContactNumber());
        return newMember;
    }

    @Override
    public LibraryMemberDeletionResponseBody deleteMember(String id) {
        libraryMemberDao.deleteById(id);
        LibraryMemberDeletionResponseBody libraryMemberDeletionResponseBody = new LibraryMemberDeletionResponseBody();
        libraryMemberDeletionResponseBody.setMessage("User deleted successfully.");
        return libraryMemberDeletionResponseBody;
    }

    public LibraryMemberUpdateResponseBody updateMember(LibraryMemberUpdateRequestBody libraryMemberUpdateRequestBody){
        String id = libraryMemberUpdateRequestBody.getUserDetailsUpdate().getId();
        Optional<LibraryMember> existingLibraryMemberOptional = libraryMemberDao.findById(id);
        if(existingLibraryMemberOptional.isPresent()){
            LibraryMember existingLibraryMember = existingLibraryMemberOptional.get();
            existingLibraryMember.setName(libraryMemberUpdateRequestBody.getUserDetailsUpdate().getName());
            existingLibraryMember.setContactNumber(libraryMemberUpdateRequestBody.getUserDetailsUpdate().getContactNumber());
            libraryMemberDao.save(existingLibraryMember);

            LibraryMemberUpdateResponseBody updateResponseBody = new LibraryMemberUpdateResponseBody();
            updateResponseBody.setMessage("Details updated successfully");
            return updateResponseBody;
        }
        else {
            LibraryMemberUpdateResponseBody failedUpdateResponse = new LibraryMemberUpdateResponseBody();
            failedUpdateResponse.setMessage("The given ID does not exist.");
            return failedUpdateResponse;
        }
    }

    public LibraryMemberFetchResponseBody getMember(String id){
        Optional<LibraryMember> existingLibraryMemberOptional = libraryMemberDao.findById(id);
        if(existingLibraryMemberOptional.isPresent()){
            LibraryMember libraryMember = existingLibraryMemberOptional.get();
            LibraryMemberDTO libraryMemberDTO = new LibraryMemberDTO();
            libraryMemberDTO.setId(libraryMember.getId());
            libraryMemberDTO.setName(libraryMember.getName());
            libraryMemberDTO.setContactNumber(libraryMember.getContactNumber());

            LibraryMemberFetchResponseBody libraryMemberFetchResponseBody = new LibraryMemberFetchResponseBody();
            libraryMemberFetchResponseBody.setUser(libraryMemberDTO);
            return libraryMemberFetchResponseBody;
        } else {
            LibraryMemberFetchResponseBody failedFetch = new LibraryMemberFetchResponseBody();
            failedFetch.setMessage("Fetch failed since given ID does not exist.");
            return failedFetch;
        }
    }
}
