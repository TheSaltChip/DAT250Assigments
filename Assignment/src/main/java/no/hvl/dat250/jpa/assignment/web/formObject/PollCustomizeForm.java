package no.hvl.dat250.jpa.assignment.web.formObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import no.hvl.dat250.jpa.assignment.models.Poll;
import no.hvl.dat250.jpa.assignment.models.PollStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PollCustomizeForm {

    @NotNull
    @Size(max = 254)
    @NonNull
    private String question;

    @NotNull
    @Size(max = 254)
    @NonNull
    private String theme;

    @NotNull
    @NonNull
    private Boolean isPrivate;

    @NotNull
    @NonNull
    private PollStatus activeStatus;
}
