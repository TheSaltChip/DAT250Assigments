package no.hvl.dat250.jpa.assignment.service.vote;

import no.hvl.dat250.jpa.assignment.models.Vote;
import no.hvl.dat250.jpa.assignment.repository.vote.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository repository) {
        this.voteRepository = repository;
    }

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public List<Vote> getAllVotesFromPoll(Long pollId) {
        return voteRepository.findAllByPoll_Id(pollId);
    }

    public List<Vote> getAllVotesFromUser(String username) {
        return voteRepository.findAllByUser_Username(username);
    }

    public Boolean hasUserVotedInPoll(String username, Long id) {
        return voteRepository.existsVoteByUser_UsernameAndPoll_Id(username, id);
    }
}
