package no.hvl.dat250.assignment.web.authenticationfacade;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();
}
