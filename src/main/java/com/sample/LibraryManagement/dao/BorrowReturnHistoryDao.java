package com.sample.LibraryManagement.dao;

import com.sample.LibraryManagement.Entity.BorrowReturnHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowReturnHistoryDao extends JpaRepository<BorrowReturnHistory, String> {
    List<BorrowReturnHistory> findByLibraryMemberIdAndIsActiveAndIsDeleted(String libraryMemberId, Boolean isActive, Boolean isDeleted);
    Optional<BorrowReturnHistory> findByLibraryMemberIdAndBookIdAndIsActiveAndIsDeleted(String libraryMemberId, String bookId, Boolean isActive, Boolean isDeleted);
}
