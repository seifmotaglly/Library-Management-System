package com.library.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.library.entity.Patron;

@Component
public class AppAuditAware implements AuditorAware<Integer> {

    @Override
    public @NonNull Optional<Integer> getCurrentAuditor() {
        Authentication authenticatedPatron = SecurityContextHolder.getContext().getAuthentication();
        if(authenticatedPatron == null || !authenticatedPatron.isAuthenticated() || authenticatedPatron instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        Patron patorn = (Patron) authenticatedPatron.getPrincipal();
        return Optional.ofNullable(patorn.getPatronId());
    }

}
