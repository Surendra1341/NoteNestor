package com.notes.notenestor.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<Integer> {

    // by user se audit
    @Override
    public Optional<Integer> getCurrentAuditor() {
        //dynamic it

        return Optional.of(1);  //hard code
    }
}
