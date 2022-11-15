package no.hvl.dat250.assignment.web.formobject;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PollCreateForm {
    @NotNull
    @Size(max = 254)
    private String question;

    @NotNull
    @Size(max = 254)
    private String theme;

    @NotNull
    private Boolean isPrivate;
}
