package no.hvl.dat250.jpa.assignment.web.controller.registration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import no.hvl.dat250.jpa.assignment.models.user.Role;
import no.hvl.dat250.jpa.assignment.models.user.User;
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
        User u = new User(username, new BCryptPasswordEncoder().encode(password), Role.Regular);

        u.setEmail(email);
        u.setFirstname(firstName);
        u.setLastname(lastName);

        return u;
    }

}