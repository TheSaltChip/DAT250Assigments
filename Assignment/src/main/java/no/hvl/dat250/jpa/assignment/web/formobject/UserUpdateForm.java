package no.hvl.dat250.jpa.assignment.web.formobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import no.hvl.dat250.jpa.assignment.models.user.User;
import org.springframework.context.annotation.Primary;

@Data
@AllArgsConstructor
public class UserUpdateForm {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
}
