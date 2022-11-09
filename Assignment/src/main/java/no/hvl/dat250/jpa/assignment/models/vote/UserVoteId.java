package no.hvl.dat250.jpa.assignment.models.vote;

import lombok.Data;
import no.hvl.dat250.jpa.assignment.models.poll.Poll;
import no.hvl.dat250.jpa.assignment.models.user.User;

import java.io.Serializable;

@Data
public class UserVoteId implements Serializable {

    private String username;
    private Long pollId;

    public UserVoteId(String username, Long pollId) {
        this.username = username;
        this.pollId = pollId;
    }

    public UserVoteId() {
    }
}
