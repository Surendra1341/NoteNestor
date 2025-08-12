package com.notes.notenestor.controller;

import com.notes.notenestor.dto.LoginRequest;
import com.notes.notenestor.dto.LoginResponse;
import com.notes.notenestor.dto.UserRequest;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.service.AuthService;
import com.notes.notenestor.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@Tag(name="UserAuthentication",description = "All the Authentication APIs")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "register success"),
            @ApiResponse(responseCode = "500",description = "Internal server error"),
            @ApiResponse(responseCode = "400",description = "BAD Request")

    })
    @Operation(summary = "User Register EndPoint")
    @PostMapping("/")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest, HttpServletRequest request) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException {
        String url = CommonUtil.getUrl(request);
        Boolean result = authService.register(userRequest, url);

        if (result) {
            return CommonUtil.createBuildResponseMessage("Register success", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Register failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "User Login EndPoint")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {

        LoginResponse loginResponse = authService.login(loginRequest);
        if (ObjectUtils.isEmpty(loginResponse)) {
            return CommonUtil.createErrorResponseMessage("invalid credential", HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
    }


}
