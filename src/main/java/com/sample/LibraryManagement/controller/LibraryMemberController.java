package com.sample.LibraryManagement.controller;

import com.sample.LibraryManagement.dto.request.LibraryMemberAddRequestBody;
import com.sample.LibraryManagement.dto.request.LibraryMemberUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;
import com.sample.LibraryManagement.service.LibraryMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library/members")
public class LibraryMemberController {

    private static final Logger logger = LoggerFactory.getLogger(LibraryMemberController.class);

    @Autowired
    LibraryMemberService libraryMemberService;

    @GetMapping("all")
    public LibraryMemberPageResponseBody getMembersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        logger.info("Request received to fetch paginated list of library members : page={}, size={}, sortBy={}, direction={}",page, size, sortBy, direction);
        return libraryMemberService.getMembersPaginated(page, size, sortBy, direction);
    }

    @PutMapping("/member")
    public LibraryMemberAddResponseBody addMember(@RequestBody LibraryMemberAddRequestBody libraryMemberAddRequestBody){
        logger.info("Request received to add a new library member.");
        return libraryMemberService.addMember(libraryMemberAddRequestBody);
    }

    @DeleteMapping("/member/{id}")
    public LibraryMemberDeletionResponseBody deleteMember(@PathVariable String id){
        logger.info("Request received to delete {}", id);
        return libraryMemberService.deleteMember(id);
    }

    @PostMapping("/member")
    public LibraryMemberUpdateResponseBody updateMember(@RequestBody LibraryMemberUpdateRequestBody libraryMemberUpdateRequestBody){
        logger.info("Request received to update details for library member ID : {}", libraryMemberUpdateRequestBody.getUserDetailsUpdate().getId());
        return libraryMemberService.updateMember(libraryMemberUpdateRequestBody);
    }

    @GetMapping("/member/{id}")
    public LibraryMemberFetchResponseBody getMember(@PathVariable String id){
        logger.info("Request received to fetch details of user ID : {}", id);
        return libraryMemberService.getMember(id);
    }
}

