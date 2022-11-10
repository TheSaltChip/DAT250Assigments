package no.hvl.dat250.jpa.assignment.authenticationfacade;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();
}
