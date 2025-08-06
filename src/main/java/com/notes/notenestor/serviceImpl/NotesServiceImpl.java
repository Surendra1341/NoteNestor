package com.notes.notenestor.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.notenestor.dto.NotesDto;
import com.notes.notenestor.entity.Category;
import com.notes.notenestor.entity.FileDetails;
import com.notes.notenestor.entity.Notes;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.repository.CategoryRepo;
import com.notes.notenestor.repository.FileRepo;
import com.notes.notenestor.repository.NotesRepo;
import com.notes.notenestor.service.NotesService;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepo notesRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Autowired
    private FileRepo fileRepo;


    @Override
    public Boolean saveNote(String Notes, MultipartFile file) throws ResourceNotFoundException, IOException {

        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto = ob.readValue(Notes, NotesDto.class);


        // validation can be done  -- > bad mai


        // category exist which we are adding  not doing anything exception fak rhe
        Category category = checkCategoryExist(notesDto.getCategory().getId());


        //mapping and save
        Notes notes = mapper.map(notesDto, Notes.class);


        // related to files
        FileDetails fileDetails = saveFileDetails(file);

        if (!ObjectUtils.isEmpty(fileDetails)) {
            notes.setFileDetails(fileDetails);
        } else {
            notes.setFileDetails(null);

        }


        Notes saveNotes = notesRepo.save(notes);


        return !ObjectUtils.isEmpty(saveNotes);

    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {
        if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

            String originalFilename = file.getOriginalFilename();


            List<String> extensionsAllowed = Arrays.asList("pdf", "doc", "xls", "xlsx", "jpg", "png", "docx", "txt");
            if (!extensionsAllowed.contains(FilenameUtils.getExtension(originalFilename))) {
                throw new IllegalArgumentException("File extension not supported");
            }

            FileDetails fileDetails = new FileDetails();
            fileDetails.setOriginalFileName(originalFilename);
            fileDetails.setDisplayFileName(getDisplayName(originalFilename));

            String randomString = UUID.randomUUID().toString();
            // using apache common io here
            //add dependencies
            String extension = FilenameUtils.getExtension(originalFilename);
            String uploadFileName = randomString + "." + extension;  // jsdskfjf.pdf

            fileDetails.setUploadFileName(uploadFileName);
            fileDetails.setSize(file.getSize());


            File saveFile = new File(uploadPath);
            if (!saveFile.exists()) saveFile.mkdirs();
            String storePath = uploadPath.concat(uploadFileName);

            fileDetails.setPath(storePath);


            // uploading done here actual
            long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
            if (upload != 0) {
                FileDetails finalSaved = fileRepo.save(fileDetails);
                return finalSaved;
            }
        }
        return null;
    }

    private String getDisplayName(String originalFilename) {
        // java_programming_tutorial.pdf
        // java_pro.pdf


        // using apache common io here
        //add dependencies
        String extension = FilenameUtils.getExtension(originalFilename);
        String fileName = FilenameUtils.removeExtension(originalFilename);

        if (fileName.length() > 8) {
            fileName = fileName.substring(0, 8);
        }
        fileName = fileName + "." + extension;
        return fileName;

    }


    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepo.findAll().stream()
                .map(notes -> mapper.map(notes, NotesDto.class)).toList();
    }

    private Category checkCategoryExist(Integer id) throws ResourceNotFoundException {
        return categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("category is invalid"));
    }
}
