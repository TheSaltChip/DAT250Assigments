package no.hvl.dat250.jpa.assignment.models.vote;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import no.hvl.dat250.jpa.assignment.models.poll.Poll;

import javax.persistence.*;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "DEVICE_VOTE", schema = "APP")
public class DeviceVote {
    @Id
    @NonNull
    private UUID uuid;

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

    protected DeviceVote() {
    }

    @Override
    public String toString() {
        return "DeviceVote{" +
                "uuid=" + uuid +
                ", poll=" + poll.getId() +
                ", yesVotes=" + yesVotes +
                ", noVotes=" + noVotes +
                '}';
    }
}
