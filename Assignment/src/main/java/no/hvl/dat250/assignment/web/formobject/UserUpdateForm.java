package no.hvl.dat250.jpa.assignment.web.formobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateForm {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
}
