package com.notes.notenestor.controller;

import com.notes.notenestor.dto.PasswordResetRequest;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.service.HomeService;
import com.notes.notenestor.service.UserService;
import com.notes.notenestor.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String token) throws ResourceNotFoundException {
        Boolean result = homeService.verifyUserAccount(uid, token);
        if (result) {
            return CommonUtil.createBuildResponseMessage("Account Verification successfully", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);
    }


    // password reset

    @GetMapping("/send-email")
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException {
        String url = CommonUtil.getUrl(request);
       userService.sendEmailPasswordReset(email,url);
       return CommonUtil.createBuildResponseMessage("Email Send Successfully !! check mail and reset password", HttpStatus.OK);

    }

    @GetMapping("/verify-password-link")
    public ResponseEntity<?> verifyPasswordResetToken(@RequestParam Integer uid,@RequestParam String token) throws ResourceNotFoundException {
        userService.verifyPasswordResetLink(uid,token);
        return CommonUtil.createBuildResponseMessage("Verified Successfully",HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws ResourceNotFoundException {
       userService.resetPassword(passwordResetRequest);
        return CommonUtil.createBuildResponseMessage("Password Reset Successfully", HttpStatus.OK);
    }
}
