package no.hvl.dat250.jpa.assignments.service.vote;

import no.hvl.dat250.jpa.assignments.models.Vote;

import java.util.List;

public interface VoteService {
    List<Vote> getAllVotes();

    List<Vote> getAllVotesFromPoll(Long pollId);

    List<Vote> getAllVotesFromUser(String username);
}
