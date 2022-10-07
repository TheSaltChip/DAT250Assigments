package no.hvl.dat250.jpa.assignmentB.models;

import javax.persistence.Embeddable;
import java.io.Serializable;


public class VotesId implements Serializable {

    private Client client;
    private Poll poll;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
