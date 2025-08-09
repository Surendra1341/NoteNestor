package com.notes.notenestor.repository;

import com.notes.notenestor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    Boolean existsByEmail(String email);
}
