package com.sample.LibraryManagement.dao;

import com.sample.LibraryManagement.Entity.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryMemberDao extends JpaRepository<LibraryMember, String> {

}
