package no.hvl.dat250.jpa.assignment.service.poll;

import no.hvl.dat250.jpa.assignment.models.Poll;
import no.hvl.dat250.jpa.assignment.models.TimeLimitPoll;
import no.hvl.dat250.jpa.assignment.models.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for poll services
 */
public interface PollService {
    Poll findById(Long pollId);

    User getOwner(Long pollId);

    Poll updateVote(boolean vote, String username, Long pollId);

    Poll updateVote(String deviceId, int yes, int no, Long pollId);

    void updatePoll(Poll poll);

    Poll closePoll(Long pollId);

    Poll openPoll(Long pollId);

    Poll updateTime(Long pollId, LocalDateTime startDate, LocalDateTime endDate);

    Poll createPoll(Poll poll);

    TimeLimitPoll createTimeLimitPoll(TimeLimitPoll poll);

    void deletePoll(Long pollId);

    List<Poll> findAllPolls();

    List<Poll> findAllOpenPolls();

    List<Poll> findAllOpenPublicPolls();

    List<Poll> findAllOpenPrivatePolls();

    List<Poll> findAllPollsByOwner(User owner);
}
