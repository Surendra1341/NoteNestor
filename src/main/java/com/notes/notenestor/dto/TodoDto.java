package com.notes.notenestor.dto;


import jdk.dynalink.beans.StaticClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    private Integer id;;

    private String title;

    private StatusDto status;

    private Integer createdBy;

    private Date createdDate;

    private Integer updatedBy;

    private Date updatedDate;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StatusDto{
        private Integer id;
        private String name;

    }

}
