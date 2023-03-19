package sn.sastrans.backofficev2.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sn.sastrans.backofficev2.security.models.UserPrincipal;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
      String fullName =   userDetails.getFirstName()+ ' ' +userDetails.getLastName();
        return Optional.ofNullable(fullName).filter(s -> !s.isEmpty());
    }
}
