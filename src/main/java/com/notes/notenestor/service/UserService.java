package com.notes.notenestor.service;

import com.notes.notenestor.dto.LoginRequest;
import com.notes.notenestor.dto.LoginResponse;
import com.notes.notenestor.dto.UserDto;
import com.notes.notenestor.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface UserService {

    Boolean register(UserDto userDto, String url) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException;

    LoginResponse login(LoginRequest request);
}
