package com.notes.notenestor.service;

import com.notes.notenestor.dto.LoginRequest;
import com.notes.notenestor.dto.LoginResponse;
import com.notes.notenestor.dto.UserRequest;
import com.notes.notenestor.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface AuthService {

    public Boolean register(UserRequest userRequest, String url) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException;

    public LoginResponse login(LoginRequest request);


}
