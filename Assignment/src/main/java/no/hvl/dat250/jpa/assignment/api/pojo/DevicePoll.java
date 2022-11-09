package no.hvl.dat250.jpa.assignment.api.pojo;

import lombok.Data;
import no.hvl.dat250.jpa.assignment.models.poll.Poll;

import java.util.UUID;

@Data
public class DevicePoll {
    private Long pollId;
    private String question;
    private String theme;
    private UUID identifier;

    public DevicePoll(Poll poll){
        pollId = poll.getId();
        question = poll.getQuestion();
        theme = poll.getTheme();
        identifier = UUID.randomUUID();
    }
}
