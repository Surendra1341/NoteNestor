package com.notes.notenestor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    private Integer id;

//    @NotBlank
//    @Min(value = 1)
//    @Max(value = 20)
    private String name;

//    @NotBlank
//    @Min(value = 1)
//    @Max(value = 100)
    private String description;

//    @NotNull
    private Boolean isActive;

    private Integer createdBy;

    private Date createdDate;

    private Integer updatedBy;

    private Date updatedDate;

}
