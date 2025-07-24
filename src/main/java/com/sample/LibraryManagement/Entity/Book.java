package com.sample.LibraryManagement.Entity;

import jakarta.persistence.*;
//import lombok.Builder;
import lombok.Data;


@Data
@Entity
//@Builder
@Table(name = "book")

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "genre")
    private String genre;
    @Column(name = "published_year")
    private String publishedYear;
    @Column(name = "is_borrowed")
    //@Builder.Default
    private Boolean isBorrowed = false; //Default for new books, will be changed if borrowed.

    @OneToOne(mappedBy = "book", targetEntity = BorrowReturnHistory.class)
    private BorrowReturnHistory borrowReturnHistory;

    @ManyToOne(targetEntity = LibraryMember.class)
    @JoinColumn(name = "library_member_id", referencedColumnName = "id")
    private LibraryMember libraryMember;
}

