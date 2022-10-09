package no.hvl.dat250.jpa.assignmentB.service.vote;

import no.hvl.dat250.jpa.assignmentB.models.Vote;

import java.util.List;

public interface VoteService {
    List<Vote> getAllVotes();

    List<Vote> getAllVotesFromPoll(Long pollId);

    List<Vote> getAllVotesFromUser(String username);
}
