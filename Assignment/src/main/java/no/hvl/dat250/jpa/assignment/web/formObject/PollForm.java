package no.hvl.dat250.jpa.assignment.web.formObject;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class PollForm {
    @NotNull
    @Max(254)
    private String question;

    @NotNull
    @Max(254)
    private String theme;

    @NotNull
    private Boolean isPrivate;
}
