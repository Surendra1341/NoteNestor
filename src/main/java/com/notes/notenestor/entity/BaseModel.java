package com.notes.notenestor.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseModel {


    private Boolean isActive;

    private Boolean isDeleted;

    private Integer createdBy;

    private Date createdDate;

    private Integer updatedBy;

    private Date updatedDate;

}
