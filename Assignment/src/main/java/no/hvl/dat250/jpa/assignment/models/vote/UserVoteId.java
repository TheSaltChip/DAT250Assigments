package no.hvl.dat250.jpa.assignment.models.vote;

import lombok.Data;

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
