package no.hvl.dat250.jpa.assignmentB.service.vote;

import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.User;
import no.hvl.dat250.jpa.assignmentB.models.Vote;
import no.hvl.dat250.jpa.assignmentB.repository.poll.PollRepository;
import no.hvl.dat250.jpa.assignmentB.repository.user.UserRepository;
import no.hvl.dat250.jpa.assignmentB.repository.vote.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final PollRepository pollRepository;
    private final UserRepository userRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository repository, PollRepository pollRepository, UserRepository userRepository) {
        this.voteRepository = repository;
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
    }

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public List<Vote> getAllVotesFromPoll(Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();
        return voteRepository.findAllByPoll(p);
    }

    public List<Vote> getAllVotesFromUser(String username){
        User u = userRepository.findById(username).orElseThrow();

        return voteRepository.findAllByUser(u);
    }

}
