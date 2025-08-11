package com.notes.notenestor.service;

import com.notes.notenestor.dto.PasswordChangeRequest;
import com.notes.notenestor.dto.PasswordResetRequest;
import com.notes.notenestor.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface UserService {

    public void changePassword(PasswordChangeRequest passwordRequest);


    void sendEmailPasswordReset(String email, String apiUrl) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException;

    void verifyPasswordResetLink(Integer uid, String token) throws ResourceNotFoundException;

    void resetPassword(PasswordResetRequest passwordResetRequest) throws ResourceNotFoundException;
}
