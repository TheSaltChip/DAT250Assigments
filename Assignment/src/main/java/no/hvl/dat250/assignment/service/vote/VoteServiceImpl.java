package no.hvl.dat250.assignment.service.vote;

import no.hvl.dat250.assignment.persistence.models.vote.UserVote;
import no.hvl.dat250.assignment.persistence.repository.vote.UserVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {
    private final UserVoteRepository userVoteRepository;

    @Autowired
    public VoteServiceImpl(UserVoteRepository repository) {
        this.userVoteRepository = repository;
    }

    public List<UserVote> getAllVotes() {
        return userVoteRepository.findAll();
    }

    public List<UserVote> getAllVotesFromPoll(Long pollId) {
        return userVoteRepository.findAllByPoll_Id(pollId);
    }

    public List<UserVote> getAllVotesFromUser(String username) {
        return userVoteRepository.findAllByUser_Username(username);
    }

    public Boolean hasUserVotedInPoll(String username, Long id) {
        return userVoteRepository.existsVoteByUser_UsernameAndPoll_Id(username, id);
    }
}
