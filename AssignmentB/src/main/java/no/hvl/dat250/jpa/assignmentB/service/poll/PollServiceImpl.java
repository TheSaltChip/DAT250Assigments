package no.hvl.dat250.jpa.assignmentB.service.poll;

import no.hvl.dat250.jpa.assignmentB.repository.poll.PollRepository;
import no.hvl.dat250.jpa.assignmentB.repository.poll.TimeLimitPollRepository;
import no.hvl.dat250.jpa.assignmentB.repository.user.UserRepository;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.TimeLimitPoll;
import no.hvl.dat250.jpa.assignmentB.models.User;
import no.hvl.dat250.jpa.assignmentB.models.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final TimeLimitPollRepository timeLimitPollRepository;
    private final UserRepository userRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository, TimeLimitPollRepository timeLimitPollRepository, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.timeLimitPollRepository = timeLimitPollRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Poll findById(Long pollId) {
        return pollRepository.findById(pollId).orElse(null);
    }


    @Override
    public User getOwner(Long pollId) {
        Poll p = pollRepository.findById(pollId).orElse(null);

        if (p == null) return null;

        return p.getOwner();
    }

    @Override
    public Poll updateVote(boolean vote, String username, Long pollId) {
        User u = userRepository.findById(username).orElseThrow();

        Poll p = pollRepository.findById(pollId).orElseThrow();

        Vote v = new Vote(u, p, vote ? 1 : 0, vote ? 0 : 1);

        p.getVotes().add(v);

        return pollRepository.save(p);
    }

    @Override
    public Poll updateVote(String deviceId, int yes, int no, Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        User d = userRepository.findById(deviceId).orElseThrow();

        Vote v = new Vote(d, p, yes, no);

        p.getVotes().add(v);

        return pollRepository.save(p);
    }

    @Override
    public void updatePoll(Poll poll) {
        Poll updatedPoll = pollRepository.getReferenceById(poll.getId());

        updatedPoll.setActive(poll.getActive());
        updatedPoll.setName(poll.getName());
        updatedPoll.setTheme(poll.getTheme());
        updatedPoll.setIsPrivate(poll.getIsPrivate());

        pollRepository.save(updatedPoll);
    }


    @Override
    public Poll closePoll(Long pollId) {
        return findAndSetActive(pollId, false);
    }


    @Override
    public Poll openPoll(Long pollId) {
        return findAndSetActive(pollId, true);
    }

    @Override
    public Poll updateTime(Long pollId, LocalDateTime startDate, LocalDateTime endDate) {
        TimeLimitPoll tlp = timeLimitPollRepository.getReferenceById(pollId);

        tlp.setStartDate(startDate);
        tlp.setEndDate(endDate);

        return timeLimitPollRepository.save(tlp);
    }

    @Override
    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    public TimeLimitPoll createTimeLimitPoll(TimeLimitPoll poll) {
        return timeLimitPollRepository.save(poll);
    }

    @Override
    public void deletePoll(Long pollId) {
        pollRepository.deleteById(pollId);
    }

    private Poll findAndSetActive(Long pollId, boolean status) {
        Poll p = pollRepository.getReferenceById(pollId);

        p.setActive(status);

        return pollRepository.save(p);
    }
}
