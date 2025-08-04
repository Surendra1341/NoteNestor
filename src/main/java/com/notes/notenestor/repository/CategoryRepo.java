package com.notes.notenestor.repository;

import com.notes.notenestor.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    
    Optional<Category> findByIdAndIsDeletedFalse(Integer id);

    List<Category> findByIsDeletedFalse();

    List<Category> findByIsActiveTrueAndIsDeletedFalse();
}
