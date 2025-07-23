package com.sample.LibraryManagement.dto;


import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BaseEntityDTO {
    private boolean isActive;
    private Boolean isDeleted;
//    private Date createdAt;
//    private Date updatedAt;
}
