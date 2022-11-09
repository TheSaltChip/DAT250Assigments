package no.hvl.dat250.jpa.assignment.authentication.facade;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();
}
