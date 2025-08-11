package com.notes.notenestor.serviceImpl;

import com.notes.notenestor.dto.EmailRequest;
import com.notes.notenestor.dto.PasswordChangeRequest;
import com.notes.notenestor.dto.PasswordResetRequest;
import com.notes.notenestor.entity.User;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.repository.UserRepo;
import com.notes.notenestor.service.UserService;
import com.notes.notenestor.util.CommonUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public void changePassword(PasswordChangeRequest passwordRequest) {
       User loggedInUser = CommonUtil.getLoggedInUser();

       if(!passwordEncoder.matches(passwordRequest.getOldPassword(), loggedInUser.getPassword())) {
           throw new IllegalArgumentException("Old password is not correct");
       }

       loggedInUser.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
       userRepo.save(loggedInUser);

    }

    @Override
    public void sendEmailPasswordReset(String email,String apiUrl) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException {
        User user = userRepo.findByEmail(email);
        if(ObjectUtils.isEmpty(user)) {
            throw new ResourceNotFoundException("Email is invalid");
        }

        // generate password reset token
        String resetToken = UUID.randomUUID().toString();

        user.getStatus().setPasswordResetToken(resetToken);
        User updateUser =userRepo.save(user);

        sendPasswordResetEmail(updateUser,apiUrl,resetToken);

    }

    @Override
    public void verifyPasswordResetLink(Integer uid, String token) throws ResourceNotFoundException {
       User user= userRepo.findById(uid).orElseThrow(()->new ResourceNotFoundException("invalid User"));
      verifyPswdResetLink(user.getStatus().getPasswordResetToken(),token);

    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) throws ResourceNotFoundException {
        User user= userRepo.findById(passwordResetRequest.getUid()).orElseThrow(()->new ResourceNotFoundException("invalid User"));
        String encodePassword = passwordEncoder.encode(passwordResetRequest.getNewPassword());
        user.setPassword(encodePassword);
        user.getStatus().setPasswordResetToken(null);
        userRepo.save(user);
    }

    private void verifyPswdResetLink(String existToken, String reqToken) {
        // not null
        if(StringUtils.hasText(reqToken)) {
            // already reset it
            if(!StringUtils.hasText(existToken)) {
                throw new IllegalArgumentException("Already done");
            }
            // invalid work done
                if(!reqToken.equals(existToken)) {
                    throw new IllegalArgumentException("Invalid Url");
                }
        }else {
            throw new IllegalArgumentException("Invalid Token");
        }
    }


    private void sendPasswordResetEmail(User savedUser, String apiUrl, String resetToken)
            throws MessagingException, UnsupportedEncodingException {

        String url = apiUrl + "/api/v1/home/verify-password-link";

        String message = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset='UTF-8'>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin:0; padding:0; }"
                + ".container { max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 8px; }"
                + ".header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #eee; }"
                + ".header h1 { color: #ff9800; margin:0; }"
                + ".content { padding: 20px; font-size: 16px; color: #333333; line-height: 1.5; }"
                + ".button { display: inline-block; padding: 12px 20px; margin-top: 20px; background: #ff9800; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; }"
                + ".footer { margin-top: 30px; font-size: 12px; color: #777777; text-align: center; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<div class='header'>"
                + "<h1>Password Reset Request</h1>"
                + "</div>"
                + "<div class='content'>"
                + "<p>Hi <b>" + savedUser.getName() + "</b>,</p>"
                + "<p>We received a request to reset your password for your NoteNestor account.</p>"
                + "<p>If you made this request, please click the button below to reset your password:</p>"
                + "<a href=\"" + url + "?uid=" + savedUser.getId() +
                "&token=" + resetToken + "\" class='button'>Reset My Password</a>"
                + "<p>If you didn’t request a password reset, you can safely ignore this email. "
                + "</p>"
                + "</div>"
                + "<div class='footer'>"
                + "<p>Thanks,<br><b>The NoteNestor Team</b></p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        EmailRequest emailRequest = EmailRequest.builder()
                .to(savedUser.getEmail())
                .subject("Password Reset Request - NoteNestor")
                .title("NoteNestor Team")
                .message(message)
                .build();

        emailService.sendEmail(emailRequest);
    }

}
