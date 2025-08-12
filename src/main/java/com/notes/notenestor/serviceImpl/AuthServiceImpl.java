package com.notes.notenestor.serviceImpl;

import com.notes.notenestor.config.security.CustomUserDetails;
import com.notes.notenestor.dto.*;
import com.notes.notenestor.entity.AccountStatus;
import com.notes.notenestor.entity.User;
import com.notes.notenestor.exception.ExistDataException;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.exception.ValidationException;
import com.notes.notenestor.repository.UserRepo;
import com.notes.notenestor.service.AuthService;
import com.notes.notenestor.service.JwtService;
import com.notes.notenestor.util.Validation;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private UserRepo userRepo;


    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public Boolean register(UserRequest userRequest, String apiUrl) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException {
        // validation
        validation.userValidation(userRequest);

        User user = mapper.map(userRequest, User.class);

        //internally
        AccountStatus status = AccountStatus
                .builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(status);


        user.setPassword(passwordEncoder.encode(user.getPassword()));


        User savedUser = userRepo.save(user);

        if (!ObjectUtils.isEmpty(savedUser)) {
            //mail send logic
            emailSend(savedUser, apiUrl);


            return true;
        }
        return false;

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {



        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));


        if (authenticate.isAuthenticated()) {
            CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();

            // account active or not check
//            if(!userRepo.findByEmail(userDetails.getUsername()).getStatus().getIsActive()) {
//                throw new ExistDataException("please active your account via email");
//            }

            String token = jwtService.generateToken(userDetails.getUser());
            LoginResponse loginResponse = LoginResponse.builder()
                    .user(mapper.map(userDetails.getUser(), UserResponse.class))
                    .token(token)
                    .build();

            return loginResponse;
        }
        return null;

    }


    // email content for registration

    private void emailSend(User savedUser, String apiUrl) throws MessagingException, UnsupportedEncodingException {

        String url = apiUrl + "/api/v1/home/verify";


        String message = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset='UTF-8'>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin:0; padding:0; }"
                + ".container { max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 8px; }"
                + ".header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #eee; }"
                + ".header h1 { color: #4CAF50; margin:0; }"
                + ".content { padding: 20px; font-size: 16px; color: #333333; line-height: 1.5; }"
                + ".button { display: inline-block; padding: 12px 20px; margin-top: 20px; background: #4CAF50; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; }"
                + ".footer { margin-top: 30px; font-size: 12px; color: #777777; text-align: center; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<div class='header'>"
                + "<h1>Welcome to NoteNestor!</h1>"
                + "</div>"
                + "<div class='content'>"
                + "<p>Hi <b>" + savedUser.getName() + "</b>,</p>"
                + "<p>Thanks for registering with us. We're excited to have you on board! 🎉</p>"
                + "<p>Please verify your account by clicking the button below:</p>"
                + "<a href=\"" + url + "?uid=" + savedUser.getId() +
                "&token=" + savedUser.getStatus().getVerificationCode() + "\" class='button'>Verify My Account</a>" + "<p>If you didn’t create this account, you can safely ignore this email.</p>"
                + "</div>"
                + "<div class='footer'>"
                + "<p>Thanks,<br><b>The NoteNestor Team</b></p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";


        EmailRequest emailRequest = EmailRequest.builder()
                .to(savedUser.getEmail())
                .subject("Welcome to NoteNestor!")
                .title("NoteNestor Team")
                .message(message)
                .build();

        emailService.sendEmail(emailRequest);

    }
}
