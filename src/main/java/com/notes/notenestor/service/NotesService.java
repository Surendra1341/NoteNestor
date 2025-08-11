package com.notes.notenestor.service;

import com.notes.notenestor.dto.NotesDto;
import com.notes.notenestor.dto.NotesResponse;
import com.notes.notenestor.entity.FileDetails;
import com.notes.notenestor.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface NotesService {


    Boolean saveNote(String Notes, MultipartFile file) throws ResourceNotFoundException, IOException;

    List<NotesDto> getAllNotes();


    byte[] downloadFile(FileDetails fileDetails) throws ResourceNotFoundException, IOException;

    FileDetails getFileDetails(Integer id) throws ResourceNotFoundException;

    NotesResponse getAllNotesByUser(Integer userID, Integer pageNo, Integer pageSize);


    NotesResponse getAllNotesBySearch(Integer userID, Integer pageNo, Integer pageSize,String keyword);

    void softDeleteNotes(Integer id) throws ResourceNotFoundException;

    void restoreNotes(Integer id) throws ResourceNotFoundException;

    List<NotesDto> getUserRecycleBInNotes(Integer userID);

    void hardDeleteNotes(Integer id) throws ResourceNotFoundException;

    void emptyRecycleBin(int userID);

    boolean copyNotes(Integer id) throws ResourceNotFoundException;


}
