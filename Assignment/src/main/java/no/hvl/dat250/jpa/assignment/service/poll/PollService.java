package no.hvl.dat250.jpa.assignment.service.poll;

import no.hvl.dat250.jpa.assignment.models.poll.Poll;
import no.hvl.dat250.jpa.assignment.models.poll.TimeLimitPoll;
import no.hvl.dat250.jpa.assignment.models.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Interface for poll services
 */
public interface PollService {
    Poll findById(Long pollId);

    User getOwner(Long pollId);

    Poll getPollByCode(Integer code);

    Poll updateUserVote(boolean vote, String username, Long pollId);

    void createDeviceVote(UUID identifier, Long id);
    void updateDeviceVote(UUID deviceId, int yes, int no, Long pollId);

    Poll updateAnonymousVote(int yes, int no);

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
