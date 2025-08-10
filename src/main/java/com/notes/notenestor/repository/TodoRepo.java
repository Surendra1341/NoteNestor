package com.notes.notenestor.repository;

import com.notes.notenestor.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TodoRepo extends JpaRepository<Todo, Integer> {


    List<Todo> findByCreatedBy(Integer userId);
}
