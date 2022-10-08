package no.hvl.dat250.jpa.assignmentB.models;

import java.io.Serializable;


public class VotesId implements Serializable {

    private User user;
    private Poll poll;

    public User getClient() {
        return user;
    }

    public void setClient(User user) {
        this.user = user;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
