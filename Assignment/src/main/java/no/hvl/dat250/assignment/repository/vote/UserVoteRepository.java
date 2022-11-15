package no.hvl.dat250.jpa.assignment.repository.vote;

import no.hvl.dat250.jpa.assignment.models.vote.UserVote;
import no.hvl.dat250.jpa.assignment.models.vote.UserVoteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserVoteRepository extends JpaRepository<UserVote, UserVoteId> {
    List<UserVote> findAllByPoll_Id(Long id);

    List<UserVote> findAllByUser_Username(String username);

    Boolean existsVoteByUser_UsernameAndPoll_Id(String username, Long id);
}
