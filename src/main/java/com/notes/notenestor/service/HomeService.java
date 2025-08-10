package com.notes.notenestor.service;


import com.notes.notenestor.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface HomeService {

    Boolean verifyUserAccount(Integer uid, String token) throws ResourceNotFoundException;
}
