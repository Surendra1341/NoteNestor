package com.notes.notenestor.repository;

import com.notes.notenestor.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<FileDetails, Integer> {

}
