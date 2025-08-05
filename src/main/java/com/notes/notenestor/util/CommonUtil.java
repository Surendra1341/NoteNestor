package com.notes.notenestor.util;

import com.notes.notenestor.dto.CategoryDto;
import com.notes.notenestor.handler.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtil {


    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus responseStatus) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(responseStatus)
                .data(data)
                .message("No problem occur while creating the response")
                .status("success")
                .build();

        return response.create();
    }


    public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus responseStatus) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(responseStatus)
                .message(message)
                .status("success")
                .build();

        return response.create();
    }


    public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus responseStatus) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(responseStatus)
                .data(data)
                .message("failed")
                .status("failed")
                .build();

        return response.create();
    }

    public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus responseStatus) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(responseStatus)
                .message(message)
                .status("failed")
                .build();

        return response.create();
    }


}
