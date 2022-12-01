package no.hvl.dat250.assignment.web.controller.registration;

import lombok.Data;
import no.hvl.dat250.assignment.persistence.models.user.Role;
import no.hvl.dat250.assignment.persistence.models.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserData {

    @NotNull
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @NotNull
    @NotEmpty(message = "User name cannot be empty")
    private String username;

    private Role role;

    @NotNull
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotNull
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    public User createUser() {
        User u = new User(username.replaceAll("\\s+", " ").trim(), new BCryptPasswordEncoder().encode(password.trim()), Role.Regular);

        u.setEmail(email.replaceAll("\\s+", " ").trim());
        u.setFirstname(firstName.replaceAll("\\s+", " ").trim());
        u.setLastname(lastName.replaceAll("\\s+", " ").trim());

        return u;
    }

}