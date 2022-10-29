package no.hvl.dat250.jpa.assignment.controller.Registration;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import no.hvl.dat250.jpa.assignment.models.Role;

@Setter
@Getter
public class UserDto {
    @NotNull
    private String firstName;

    @NotNull
    private String username;

    @NotNull
    private Role role;

    @NotNull
    private String lastName;

    @NotNull
    private String password;
    private String matchingPassword;

    @NotNull
    private String email;


}