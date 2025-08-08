package com.notes.notenestor.repository;

import com.notes.notenestor.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface NotesRepo extends JpaRepository<Notes, Integer> {


    List<Notes> findByCreatedByAndIsDeletedTrue(Integer userID);

    Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userID, Pageable pageable);


    List<Notes> findAllByIsDeletedAndDeletedAtBefore(boolean b, LocalDateTime cutOffDate);
}
