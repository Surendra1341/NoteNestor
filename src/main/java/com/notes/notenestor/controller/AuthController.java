package com.notes.notenestor.controller;

import com.notes.notenestor.dto.LoginRequest;
import com.notes.notenestor.dto.LoginResponse;
import com.notes.notenestor.dto.UserDto;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.service.UserService;
import com.notes.notenestor.util.CommonUtil;
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

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/")
    public ResponseEntity<?> register(@RequestBody UserDto userDto, HttpServletRequest request) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException {
        String url = CommonUtil.getUrl(request);
        Boolean result = userService.register(userDto, url);

        if (result) {
            return CommonUtil.createBuildResponseMessage("Register success", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Register failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(LoginRequest loginRequest) throws Exception {

        LoginResponse loginResponse = userService.login(loginRequest);
        if (ObjectUtils.isEmpty(loginResponse)) {
            return CommonUtil.createErrorResponseMessage("invalid credential", HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuildResponse(loginResponse,HttpStatus.OK);
    }



}
