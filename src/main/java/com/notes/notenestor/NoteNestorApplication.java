package com.notes.notenestor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")   // bean ka name
@EnableScheduling   // scheduling
public class NoteNestorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteNestorApplication.class, args);
    }

}
