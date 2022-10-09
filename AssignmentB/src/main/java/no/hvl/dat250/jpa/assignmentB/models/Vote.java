package no.hvl.dat250.jpa.assignmentB.models;

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
    @JsonBackReference
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
}