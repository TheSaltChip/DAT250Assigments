package no.hvl.dat250.jpa.assignment.controller.Registration;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import no.hvl.dat250.jpa.assignment.models.Role;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Setter
@Getter
public class UserData implements Serializable {
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @NotEmpty(message = "User name cannot be empty")
    private String username;

    @NotNull
    private Role role;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    private String email;

}