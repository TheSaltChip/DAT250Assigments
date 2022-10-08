package no.hvl.dat250.jpa.assignmentB.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "VOTES", schema = "APP")
@IdClass(VotesId.class)
public class Vote {

    @Id
    @OneToOne(optional = false)
    @JoinColumn(name = "client_username", referencedColumnName = "USERNAME", nullable = false)
    private User user;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "poll_id", referencedColumnName = "ID", nullable = false)
    private Poll poll;

    private Integer yesVotes;
    private Integer noVotes;


}