package com.notes.notenestor.schedular;


import com.notes.notenestor.entity.Notes;
import com.notes.notenestor.repository.NotesRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesSchedular {


    private final NotesRepo notesRepo;

    public NotesSchedular(NotesRepo notesRepo) {
        this.notesRepo = notesRepo;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteNotesSchedular() {
        // 09 / 08/ 2025   - 7days =->
        LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);

        List<Notes> deleteNotes = notesRepo.findAllByIsDeletedAndDeletedAtBefore(true, cutOffDate);

        notesRepo.deleteAll(deleteNotes);  // delete permanent
    }
}
