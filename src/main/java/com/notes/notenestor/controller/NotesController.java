package com.notes.notenestor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.notes.notenestor.dto.NotesDto;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.service.NotesService;
import com.notes.notenestor.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService service;
    @Autowired
    private NotesService notesService;

    @PostMapping("/")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam (required = false) MultipartFile file) throws ResourceNotFoundException, IOException {

       Boolean saved = service.saveNote(notes,file);
       if (saved) {
           return CommonUtil.createBuildResponseMessage("notes saved Successfully", HttpStatus.CREATED);
       }
       return CommonUtil.createErrorResponseMessage("notes saving Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/")
    public ResponseEntity<?> getAllNotes() throws ResourceNotFoundException {

        List<NotesDto> notes = service.getAllNotes();

        if(CollectionUtils.isEmpty(notes)) {
            return CommonUtil.createErrorResponseMessage("No notes found", HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }


}
