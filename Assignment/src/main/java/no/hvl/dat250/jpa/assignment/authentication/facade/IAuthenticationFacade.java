package no.hvl.dat250.jpa.assignment.authentication.facade;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
