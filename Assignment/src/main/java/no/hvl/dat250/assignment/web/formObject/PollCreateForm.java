package no.hvl.dat250.assignment.web.formObject;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PollCreateForm {
    @NotNull
    @Size(min = 2, max = 254)
    private String question;

    @NotNull
    @Size(min = 2, max = 254)
    private String theme;

    @NotNull
    private Boolean isPrivate;
}
