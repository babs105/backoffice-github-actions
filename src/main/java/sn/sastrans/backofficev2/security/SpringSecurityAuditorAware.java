package sn.sastrans.backofficev2.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sn.sastrans.backofficev2.security.models.UserPrincipal;

import java.util.Optional;
@Slf4j
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
//
//        log.info("DATEESR :"+ authentication.getPrincipal());
//        authentication.getPrincipal();
//      String fullName =   userDetails.getFirstName()+ ' ' +userDetails.getLastName();
        return Optional.ofNullable(username).filter(s -> !s.isEmpty());
    }
}
