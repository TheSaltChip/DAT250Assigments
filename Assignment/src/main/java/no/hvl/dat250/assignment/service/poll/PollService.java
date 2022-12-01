package no.hvl.dat250.assignment.service.poll;

import no.hvl.dat250.assignment.persistence.models.poll.Poll;
import no.hvl.dat250.assignment.persistence.models.poll.TimeLimitPoll;
import no.hvl.dat250.assignment.persistence.models.user.User;
import no.hvl.dat250.assignment.web.formObject.PollCustomizeForm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Interface for poll services
 */
public interface PollService {
    Poll findById(Long pollId) throws NoSuchElementException;

    User getOwner(Long pollId) throws NoSuchElementException;

    Poll getPollByCode(Integer code) throws NoSuchElementException;

    Poll updateUserVote(boolean vote, String username, Long pollId) throws NoSuchElementException;

    void createDeviceVote(UUID identifier, Long id) throws NoSuchElementException;

    void updateDeviceVote(UUID deviceId, int yes, int no, Long pollId) throws NoSuchElementException;

    void createAnonymousVote(Poll poll, boolean vote) throws NoSuchElementException;

    void updatePoll(Poll poll) throws NoSuchElementException;

    void updatePoll(Long id, PollCustomizeForm pcf) throws NoSuchElementException;

    Poll closePoll(Long pollId) throws NoSuchElementException;

    Poll openPoll(Long pollId) throws NoSuchElementException;

    Poll updateTime(Long pollId, LocalDateTime startDate, LocalDateTime endDate) throws NoSuchElementException;

    Poll createPoll(Poll poll);


    TimeLimitPoll createTimeLimitPoll(TimeLimitPoll poll);

    void deletePoll(Long pollId);

    List<Poll> findAllPolls();

    List<Poll> findAllOpenPolls();

    List<Poll> findAllOpenPublicPolls();

    List<Poll> findAllOpenPrivatePolls();

    List<Poll> findAllPollsByOwner(User owner);
}
