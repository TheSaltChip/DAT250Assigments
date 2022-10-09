package no.hvl.dat250.jpa.assignmentB.repository.vote;

import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.User;
import no.hvl.dat250.jpa.assignmentB.models.Vote;
import no.hvl.dat250.jpa.assignmentB.models.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {
    List<Vote> findAllByPoll(Poll poll);
    List<Vote> findAllByUser(User user);
}
