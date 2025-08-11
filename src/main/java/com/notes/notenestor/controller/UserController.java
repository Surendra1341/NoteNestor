package com.notes.notenestor.controller;


import com.notes.notenestor.dto.PasswordChangeRequest;
import com.notes.notenestor.dto.UserResponse;
import com.notes.notenestor.entity.User;
import com.notes.notenestor.service.UserService;
import com.notes.notenestor.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        User user = CommonUtil.getLoggedInUser();
        UserResponse userResponse = mapper.map(user, UserResponse.class);
        return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
    }

    @GetMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordRequest) {
       userService.changePassword(passwordRequest);
        return CommonUtil.createBuildResponseMessage("Password Change Successfully", HttpStatus.OK);
    }

}
