package no.hvl.dat250.jpa.assignment.message;

import lombok.Data;
import lombok.NonNull;

@Data
public class PollAnalytic {

    @NonNull
    private String question;

    @NonNull
    private String theme;

    @NonNull
    private String owner;

    @NonNull
    private Integer yesVotes;

    @NonNull
    private Integer noVotes;

    @NonNull
    private Integer deviceVotes;

    @NonNull
    private Integer userVotes;

    @NonNull
    private Integer anonymousVotes;
}
