package no.hvl.dat250.assignment.models.vote;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NonNull;
import no.hvl.dat250.assignment.models.poll.Poll;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Poll poll;

    @NonNull
    private Integer yesVotes = 0;
    @NonNull
    private Integer noVotes = 0;

    protected AnonymousVote() {
    }

    public AnonymousVote(@NonNull Poll poll, boolean vote) {
        this.poll = poll;
        if (vote) {
            yesVotes++;
        } else {
            noVotes++;
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
