package com.notes.notenestor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    private Integer id;

    private String name;

    private String description;

    private Boolean isActive;

    private Integer createdBy;

    private Date createdDate;

    private Integer updatedBy;

    private Date updatedDate;

}
