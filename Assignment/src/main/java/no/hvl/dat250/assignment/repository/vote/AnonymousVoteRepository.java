package no.hvl.dat250.assignment.repository.vote;

import no.hvl.dat250.assignment.models.vote.AnonymousVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnonymousVoteRepository extends JpaRepository<AnonymousVote, Long> {
    List<AnonymousVote> findAllByPoll_Id(Long pollId);
}
