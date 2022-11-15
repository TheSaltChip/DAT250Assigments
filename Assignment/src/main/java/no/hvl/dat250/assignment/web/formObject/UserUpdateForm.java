package no.hvl.dat250.assignment.web.formObject;

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
