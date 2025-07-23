package com.sample.LibraryManagement.controller;

import com.sample.LibraryManagement.dto.request.LibraryMemberAddRequestBody;
import com.sample.LibraryManagement.dto.request.LibraryMemberUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;
import com.sample.LibraryManagement.service.LibraryMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library/members")
public class LibraryMemberController {
    @Autowired
    LibraryMemberService libraryMemberService;

    @GetMapping("all")
    public LibraryMemberListResponseBody getAllMembers(){
        return libraryMemberService.getAllUsers();
    }

    @PutMapping("/member")
    public LibraryMemberAddResponseBody addMember(@RequestBody LibraryMemberAddRequestBody libraryMemberAddRequestBody){
        return libraryMemberService.addMember(libraryMemberAddRequestBody);
    }

    @DeleteMapping("/member/{id}")
    public LibraryMemberDeletionResponseBody deleteMember(@PathVariable String id){
        return libraryMemberService.deleteMember(id);
    }

    @PostMapping("/member")
    public LibraryMemberUpdateResponseBody updateMember(@RequestBody LibraryMemberUpdateRequestBody libraryMemberUpdateRequestBody){
        return libraryMemberService.updateMember(libraryMemberUpdateRequestBody);
    }

    @GetMapping("/member/{id}")
    public LibraryMemberFetchResponseBody getMember(@PathVariable String id){
        return libraryMemberService.getMember(id);
    }
}

