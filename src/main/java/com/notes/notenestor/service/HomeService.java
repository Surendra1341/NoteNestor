package com.notes.notenestor.service;


import com.notes.notenestor.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public interface HomeService {

    public Boolean verifyUserAccount(Integer uid,String token) throws ResourceNotFoundException;
}
