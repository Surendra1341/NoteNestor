package com.notes.notenestor.repository;

import com.notes.notenestor.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepo extends JpaRepository<Notes, Integer> {
}
