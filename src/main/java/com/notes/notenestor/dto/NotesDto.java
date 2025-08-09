package com.notes.notenestor.dto;


import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotesDto {


    private Integer id;

    private String title;

    private String description;

    @ManyToOne
    private CategoryDto category;

    private FileDto fileDetails;

    private Integer createdBy;

    private Date createdDate;

    private Integer updatedBy;

    private Date updatedDate;

    private Boolean isDeleted;

    private LocalDateTime deletedAt;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDto {
        private Integer id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileDto {
        private Integer id;

        private String originalFileName;

        private String displayFileName;


    }

}
