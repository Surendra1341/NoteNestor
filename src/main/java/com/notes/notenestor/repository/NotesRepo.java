package com.notes.notenestor.repository;

import com.notes.notenestor.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepo extends JpaRepository<Notes, Integer> {


    List<Notes> findByCreatedByAndIsDeletedTrue(Integer userID);

    Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userID, Pageable pageable);


    List<Notes> findAllByIsDeletedAndDeletedAtBefore(boolean b, LocalDateTime cutOffDate);


    @Query("""
       select n from Notes n
       where (
           lower(n.title) like lower(concat('%', :keyword, '%'))
           or lower(n.description) like lower(concat('%', :keyword, '%'))
           or lower(n.category.name) like lower(concat('%', :keyword, '%'))
       )
       and n.isDeleted = false
       and n.createdBy = :userId
       """)
    Page<Notes> searchNotes(@Param("keyword") String keyword, @Param("userId") Integer userID, Pageable pageable);
}
