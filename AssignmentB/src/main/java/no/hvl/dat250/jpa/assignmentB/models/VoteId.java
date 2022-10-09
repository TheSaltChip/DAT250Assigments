package no.hvl.dat250.jpa.assignmentB.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoteId implements Serializable {

    private User user;
    private Poll poll;

    public VoteId(User user, Poll poll) {
        this.user = user;
        this.poll = poll;
    }

    public VoteId() {
    }
}
