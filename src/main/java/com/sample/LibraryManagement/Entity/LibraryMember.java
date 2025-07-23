package com.sample.LibraryManagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "library_member")
public class LibraryMember {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "contact_number")
    private String contactNumber;

    @OneToMany(mappedBy = "libraryMember", targetEntity = Book.class)
    @JsonIgnore
    private List<Book> books;

    @OneToMany(mappedBy = "libraryMember", targetEntity = BorrowReturnHistory.class)
    private List<BorrowReturnHistory> borrowHistories;



}
