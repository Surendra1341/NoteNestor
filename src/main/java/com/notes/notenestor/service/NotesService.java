package com.notes.notenestor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.notes.notenestor.dto.NotesDto;
import com.notes.notenestor.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface NotesService {


    public Boolean saveNote(String Notes, MultipartFile file) throws ResourceNotFoundException, IOException;

    public List<NotesDto> getAllNotes();




}
