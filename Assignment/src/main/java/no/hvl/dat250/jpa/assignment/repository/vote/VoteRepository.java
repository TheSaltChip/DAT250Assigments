package no.hvl.dat250.jpa.assignment.repository.vote;

import no.hvl.dat250.jpa.assignment.models.Poll;
import no.hvl.dat250.jpa.assignment.models.User;
import no.hvl.dat250.jpa.assignment.models.Vote;
import no.hvl.dat250.jpa.assignment.models.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {
    List<Vote> findAllByPoll(Poll poll);
    List<Vote> findAllByUser(User user);
}
