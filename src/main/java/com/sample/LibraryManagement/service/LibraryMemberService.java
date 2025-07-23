package com.sample.LibraryManagement.service;

import com.sample.LibraryManagement.dto.request.LibraryMemberAddRequestBody;
import com.sample.LibraryManagement.dto.request.LibraryMemberUpdateRequestBody;
import com.sample.LibraryManagement.dto.response.*;

public interface LibraryMemberService {
    public LibraryMemberListResponseBody getAllUsers();
    public LibraryMemberAddResponseBody addMember(LibraryMemberAddRequestBody libraryMemberAddRequestBody);
    public LibraryMemberDeletionResponseBody deleteMember(String id);
    public LibraryMemberUpdateResponseBody updateMember(LibraryMemberUpdateRequestBody libraryMemberUpdateRequestBody);
    public LibraryMemberFetchResponseBody getMember(String id);
}
