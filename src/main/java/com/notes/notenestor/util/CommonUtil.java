package com.notes.notenestor.util;

import com.notes.notenestor.config.security.CustomUserDetails;
import com.notes.notenestor.dto.UserResponse;
import com.notes.notenestor.entity.User;
import com.notes.notenestor.handler.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

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


    public static String getContentType(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName); // java_programing.pdf

        switch (extension) {
            case "pdf":
                return "application/pdf";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
            case "txt":
                return "text/plan";
            case "png":
                return "image/png";
            case "jpeg":
                return "image/jpeg";
            default:
                return "application/octet-stream";
        }
    }

    public static String getUrl(HttpServletRequest request) {
        String fullUrl = request.getRequestURL().toString();  // http://localhost:8080/api/v1/auth/
        String path = request.getServletPath();  //  /api/v1/auth/
        return fullUrl.replace(path, "");  //  http://localhost:8080
    }

        public static User getLoggedInUser() {
        try {
            CustomUserDetails loggedUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return loggedUser.getUser();
        }catch (Exception e) {
            throw e;
        }
        }
}
