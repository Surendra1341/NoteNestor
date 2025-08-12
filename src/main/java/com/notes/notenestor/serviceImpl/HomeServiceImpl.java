package com.notes.notenestor.serviceImpl;

import com.notes.notenestor.entity.User;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.exception.SuccessException;
import com.notes.notenestor.repository.UserRepo;
import com.notes.notenestor.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public Boolean verifyUserAccount(Integer uid, String token) throws ResourceNotFoundException {
        log.info("HomeServiceImpl  :: verifyUserAccount  ::" + uid);
        User user = userRepo.findById(uid).orElseThrow(() -> new ResourceNotFoundException("Invalid user"));

        if (user.getStatus().getVerificationCode() == null) {
            throw new SuccessException("Account Already Verified");
        }

        if (user.getStatus().getVerificationCode().equals(token)) {
            user.getStatus().setVerificationCode(null);
            user.getStatus().setIsActive(true);

            userRepo.save(user);

            return true;
        }
        return false;
    }
}
