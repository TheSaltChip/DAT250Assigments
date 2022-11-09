package no.hvl.dat250.jpa.assignment.controller.Registration;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import no.hvl.dat250.jpa.assignment.models.user.Role;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
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

}