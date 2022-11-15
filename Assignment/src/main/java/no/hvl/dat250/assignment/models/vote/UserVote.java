package no.hvl.dat250.assignment.models.vote;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import no.hvl.dat250.assignment.models.poll.Poll;
import no.hvl.dat250.assignment.models.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "USER_VOTE", schema = "APP")
@IdClass(UserVoteId.class)
public class UserVote implements Serializable {

    @Id
    @Column(name = "client_username")
    @NonNull
    private String username;

    @Id
    @Column(name = "poll_id")
    @NonNull
    private Long pollId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_username", referencedColumnName = "USERNAME", nullable = false, insertable = false, updatable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
    @JsonIdentityReference(alwaysAsId = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "poll_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Poll poll;

    @NonNull
    private Integer yesVotes;
    @NonNull
    private Integer noVotes;

    protected UserVote() {
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "user=" + username +
                ", poll=" + pollId +
                ", yesVotes=" + yesVotes +
                ", noVotes=" + noVotes +
                '}';
    }
}