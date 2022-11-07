package no.hvl.dat250.jpa.assignment.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "VOTE", schema = "APP")
@IdClass(VoteId.class)
public class Vote implements Serializable {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_username", referencedColumnName = "USERNAME", nullable = false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="username")
    @JsonIdentityReference(alwaysAsId=true)
    private User user;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "poll_id", referencedColumnName = "ID", nullable = false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private Poll poll;

    @NonNull
    private Integer yesVotes;
    @NonNull
    private Integer noVotes;

    public Vote(User user, Poll poll, @NonNull Integer yesVotes, @NonNull Integer noVotes) {
        this.user = user;
        this.poll = poll;
        this.yesVotes = yesVotes;
        this.noVotes = noVotes;
    }

    protected Vote() {
    }

    @Override
    public String toString() {
        return "Vote{" +
                "user=" + user.getUsername() +
                ", poll=" + poll.getId() +
                ", yesVotes=" + yesVotes +
                ", noVotes=" + noVotes +
                '}';
    }
}