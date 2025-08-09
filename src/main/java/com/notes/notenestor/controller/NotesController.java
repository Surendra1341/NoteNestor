package com.notes.notenestor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.notes.notenestor.dto.NotesDto;
import com.notes.notenestor.dto.NotesResponse;
import com.notes.notenestor.entity.FileDetails;
import com.notes.notenestor.entity.Notes;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.service.NotesService;
import com.notes.notenestor.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService service;
    @Autowired
    private NotesService notesService;

    // save or update
    @PostMapping("/")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam (required = false) MultipartFile file) throws ResourceNotFoundException, IOException {

       Boolean saved = service.saveNote(notes,file);
       if (saved) {
           return CommonUtil.createBuildResponseMessage("notes saved Successfully", HttpStatus.CREATED);
       }
       return CommonUtil.createErrorResponseMessage("notes saving Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // ye admin use ke lye hai
    @GetMapping("/")
    public ResponseEntity<?> getAllNotes() throws ResourceNotFoundException {

        List<NotesDto> notes = service.getAllNotes();

        if(CollectionUtils.isEmpty(notes)) {
            return CommonUtil.createErrorResponseMessage("No notes found", HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws ResourceNotFoundException, IOException {
       FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] downloadFile =notesService.downloadFile(fileDetails);

        HttpHeaders headers = new HttpHeaders();
        String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

        return ResponseEntity.ok().headers(headers).body(downloadFile);
    }


    @GetMapping("/user-notes")
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam(name = "pageNo",defaultValue = "0")Integer pageNo,
            @RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize

    ) throws ResourceNotFoundException {
        // yha pageNo. follow index type rule
        Integer userID=1;


        NotesResponse notes = service.getAllNotesByUser(userID,pageNo,pageSize);


        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }



    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws ResourceNotFoundException {
       notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Delete success :)", HttpStatus.OK);

    }


    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws ResourceNotFoundException {
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("restore success :)", HttpStatus.OK);

    }

    @GetMapping("/recycle-bin")
    public ResponseEntity<?> getUserRecycleNotes() throws ResourceNotFoundException {
        Integer userID=1;
        List<NotesDto> notes = notesService.getUserRecycleBInNotes(userID);

        if(CollectionUtils.isEmpty(notes)) {
            return CommonUtil.createErrorResponseMessage("No notes found", HttpStatus.NOT_FOUND);
        }
        // if no notes handle by UI
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws ResourceNotFoundException {
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Delete success :)", HttpStatus.OK);

    }

    @DeleteMapping("/delete-recycle")
    public ResponseEntity<?> emptyRecycleBin() throws ResourceNotFoundException {
        int userID=1;
        notesService.emptyRecycleBin(userID);
        return CommonUtil.createBuildResponseMessage("Delete success :)", HttpStatus.OK);
    }


    //add favorite if need -> upcoming features



    @GetMapping("/copy/{id}")
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws ResourceNotFoundException {
        boolean copied =notesService.copyNotes(id);
        if (copied){
            return CommonUtil.createBuildResponseMessage("Copy success :)", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Copy Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
