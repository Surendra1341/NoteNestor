package com.notes.notenestor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Integer id;

    private String name;

    private String email;

    private StatusDto status;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StatusDto {

        private Integer id;

        private Boolean isActive;

    }


}
