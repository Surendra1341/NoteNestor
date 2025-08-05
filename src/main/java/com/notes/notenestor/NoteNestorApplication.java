package com.notes.notenestor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")   // bean ka name
public class NoteNestorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteNestorApplication.class, args);
    }

}
