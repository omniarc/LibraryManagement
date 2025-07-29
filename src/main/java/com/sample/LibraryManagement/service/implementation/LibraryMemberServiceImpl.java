package com.sample.LibraryManagement.service.implementation;

import com.sample.LibraryManagement.Entity.LibraryMember;
import com.sample.LibraryManagement.dao.LibraryMemberDao;
import com.sample.LibraryManagement.dto.LibraryMemberDTO;
import com.sample.LibraryManagement.dto.request.LibraryMemberAddRequestBody;
import com.sample.LibraryManagement.dto.request.LibraryMemberUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;
import com.sample.LibraryManagement.service.LibraryMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {

    private static final Logger logger = LoggerFactory.getLogger(LibraryMemberService.class);

    @Autowired
    private LibraryMemberDao libraryMemberDao;

    @Override
    public LibraryMemberAddResponseBody addMember(LibraryMemberAddRequestBody libraryMemberAddRequestBody){
        LibraryMember newMember = LibraryMemberDTO.toLibraryMember(libraryMemberAddRequestBody);
        libraryMemberDao.save(newMember);
        LibraryMemberAddResponseBody libraryMemberAddResponseBody = new LibraryMemberAddResponseBody();
        libraryMemberAddResponseBody.setMessage("User added successfully.");
        logger.info("Request to add a new library member processed successfully.");
        return libraryMemberAddResponseBody;

    }

    @Override
    public LibraryMemberDeletionResponseBody deleteMember(String id) {
        libraryMemberDao.deleteById(id);
        LibraryMemberDeletionResponseBody libraryMemberDeletionResponseBody = new LibraryMemberDeletionResponseBody();
        libraryMemberDeletionResponseBody.setMessage("User deleted successfully.");
        logger.info("User deletion request processed successfully.");
        return libraryMemberDeletionResponseBody;
    }

    public LibraryMemberUpdateResponseBody updateMember(LibraryMemberUpdateRequestBody libraryMemberUpdateRequestBody){
        String id = libraryMemberUpdateRequestBody.getUserDetailsUpdate().getId();
        Optional<LibraryMember> existingLibraryMemberOptional = libraryMemberDao.findById(id);
        if(existingLibraryMemberOptional.isPresent()){
            LibraryMember existingLibraryMember = existingLibraryMemberOptional.get();
            LibraryMemberDTO.updateLibraryMemberRequest(existingLibraryMember, libraryMemberUpdateRequestBody);
            libraryMemberDao.save(existingLibraryMember);

            LibraryMemberUpdateResponseBody updateResponseBody = new LibraryMemberUpdateResponseBody();
            logger.info("User updation request processed successfully.");
            updateResponseBody.setMessage("Details updated successfully");
            return updateResponseBody;
        }
        else {
            LibraryMemberUpdateResponseBody failedUpdateResponse = new LibraryMemberUpdateResponseBody();
            failedUpdateResponse.setMessage("The given ID does not exist.");
            logger.info("User updation request exited, since given ID is invalid or does not exist.");
            return failedUpdateResponse;
        }
    }

    @Cacheable(value = "libraryMembers", key = "#id")
    public LibraryMemberFetchResponseBody getMember(String id){
        Optional<LibraryMember> existingLibraryMemberOptional = libraryMemberDao.findById(id);
        if(existingLibraryMemberOptional.isPresent()){
            LibraryMember libraryMember = existingLibraryMemberOptional.get();
            LibraryMemberDTO libraryMemberDTO = LibraryMemberDTO.fromLibraryMember(libraryMember);
            LibraryMemberFetchResponseBody libraryMemberFetchResponseBody = new LibraryMemberFetchResponseBody();
            libraryMemberFetchResponseBody.setUser(libraryMemberDTO);
            logger.info("Member fetch request processed successfully.");
            return libraryMemberFetchResponseBody;
        } else {
            LibraryMemberFetchResponseBody failedFetch = new LibraryMemberFetchResponseBody();
            failedFetch.setMessage("Fetch failed since given ID does not exist.");
            logger.info("Member fetch request failed since given ID does not exist, or is invalid.");
            return failedFetch;
        }
    }

    @Override
    public LibraryMemberPageResponseBody getMembersPaginated(int page, int size, String sortBy, String direction){
        Sort sortMember = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sortMember);

        Page<LibraryMember> pagedResult = libraryMemberDao.findAll(pageable);

        List<LibraryMemberDTO> libraryMemberDTOList = pagedResult.getContent().stream()
                .map(LibraryMemberDTO::fromLibraryMember)
                .toList();

        LibraryMemberPageResponseBody libraryMemberPageResponseBody = new LibraryMemberPageResponseBody();
        libraryMemberPageResponseBody.setData1(libraryMemberDTOList);
        libraryMemberPageResponseBody.setPageNumber(pagedResult.getNumber());
        libraryMemberPageResponseBody.setPageSize(pagedResult.getSize());
        libraryMemberPageResponseBody.setTotalElements(pagedResult.getTotalElements());
        libraryMemberPageResponseBody.setTotalPages(pagedResult.getTotalPages());
        libraryMemberPageResponseBody.setLast(pagedResult.isLast());


        logger.info("Paginated book fetch: page {} of {}", page, pagedResult.getTotalPages());
        return libraryMemberPageResponseBody;

    }
}
