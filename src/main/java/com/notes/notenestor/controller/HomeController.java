package com.notes.notenestor.controller;

import com.notes.notenestor.entity.User;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.repository.UserRepo;
import com.notes.notenestor.service.HomeService;
import com.notes.notenestor.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid,@RequestParam String token) throws ResourceNotFoundException {
        Boolean result = homeService.verifyUserAccount(uid,token);
        if (result) {
            return CommonUtil.createBuildResponseMessage("Account Verification successfully", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);
    }
}
