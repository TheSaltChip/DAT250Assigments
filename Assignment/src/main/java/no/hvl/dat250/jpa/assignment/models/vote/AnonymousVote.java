package no.hvl.dat250.jpa.assignment.models.vote;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NonNull;
import no.hvl.dat250.jpa.assignment.models.poll.Poll;

import javax.persistence.*;

@Entity
@Table(name = "ANONYMOUS_VOTE", schema = "APP")
@Data
public class AnonymousVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "poll_id", referencedColumnName = "ID", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Poll poll;

    @NonNull
    private Integer yesVotes;
    @NonNull
    private Integer noVotes;

    protected AnonymousVote() {
    }

    public AnonymousVote(Poll poll, boolean vote){
        this.poll = poll;
        if(vote){
            yesVotes ++;
        }else {
            noVotes ++;
        }
    }

    @Override
    public String toString() {
        return "AnonymousVote{" +
                "id=" + id +
                ", poll=" + poll.getId() +
                ", yesVote=" + yesVotes +
                ", noVote=" + noVotes +
                '}';
    }
}
