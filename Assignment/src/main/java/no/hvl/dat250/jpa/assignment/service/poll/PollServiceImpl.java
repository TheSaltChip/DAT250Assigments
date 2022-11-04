package no.hvl.dat250.jpa.assignment.service.poll;

import no.hvl.dat250.jpa.assignment.models.*;
import no.hvl.dat250.jpa.assignment.repository.poll.PollRepository;
import no.hvl.dat250.jpa.assignment.repository.poll.TimeLimitPollRepository;
import no.hvl.dat250.jpa.assignment.repository.user.UserRepository;
import no.hvl.dat250.jpa.assignment.repository.vote.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final TimeLimitPollRepository timeLimitPollRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository, TimeLimitPollRepository timeLimitPollRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.timeLimitPollRepository = timeLimitPollRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public Poll findById(Long pollId) {
        return pollRepository.findById(pollId).orElseThrow();
    }


    @Override
    public User getOwner(Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow(NoSuchElementException::new);

        return p.getOwner();
    }

    @Override
    public Poll updateVote(boolean vote, String username, Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        if (!p.getActiveStatus().equals(PollStatus.OPEN)) return p;

        User u = userRepository.findById(username).orElseThrow();

        if(!u.getRole().equals(Role.Regular)){
            return p;
        }

        Optional<Vote> oV = voteRepository.findById(new VoteId(u, p));

        if (oV.isPresent()) {
            Vote v = oV.get();
            v.setYesVotes(vote ? 1 : 0);
            v.setNoVotes(vote ? 0 : 1);

            p.getVotes().remove(v);
            p.getVotes().add(v);

            return pollRepository.save(p);
        }

        Vote v = new Vote(u, p, vote ? 1 : 0, vote ? 0 : 1);

        p.getVotes().add(v);

        return pollRepository.save(p);
    }

    @Override
    public Poll updateVote(String deviceId, int yes, int no, Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        // Custom response should be given here telling that the poll is closed
        if (!p.getActiveStatus().equals(PollStatus.OPEN)) return p;

        User d = userRepository.findById(deviceId).orElseThrow();

        System.out.println(d.getRole());
        System.out.println(d.getRole().equals(Role.Device));

        if(!d.getRole().equals(Role.Device)){
            return p;
        }

        Optional<Vote> oVote = voteRepository.findById(new VoteId(d, p));

        if (oVote.isEmpty()) {
            Vote v = new Vote(d, p, yes, no);

            p.getVotes().add(v);

            return pollRepository.save(p);
        }

        Vote v = oVote.get();

        v.setNoVotes(v.getNoVotes() + no);
        v.setYesVotes(v.getYesVotes() + yes);

        p.getVotes().remove(v);
        p.getVotes().add(v);

        return pollRepository.save(p);
    }

    @Override
    public void updatePoll(Poll poll) {
        Poll updatedPoll = pollRepository.findById(poll.getId()).orElseThrow();

        updatedPoll.setActiveStatus(poll.getActiveStatus());
        updatedPoll.setQuestion(poll.getQuestion());
        updatedPoll.setTheme(poll.getTheme());
        updatedPoll.setIsPrivate(poll.getIsPrivate());

        pollRepository.save(updatedPoll);
    }


    @Override
    public Poll closePoll(Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        p.setActiveStatusToFinished();

        return pollRepository.save(p);
    }


    @Override
    public Poll openPoll(Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        p.setActiveStatusToOpen();

        return pollRepository.save(p);
    }

    @Override
    public Poll updateTime(Long pollId, LocalDateTime startDate, LocalDateTime endDate) {
        TimeLimitPoll tlp = timeLimitPollRepository.findById(pollId).orElseThrow();

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

    @Override
    public List<Poll> findAllPolls() {
        return pollRepository.findAll();
    }

    @Override
    public List<Poll> findAllOpenPolls() {
        return pollRepository.findAllByActiveStatus(PollStatus.OPEN);
    }

    @Override
    public List<Poll> findAllOpenPublicPolls() {
        return pollRepository.findAllByActiveStatusAndIsPrivate(PollStatus.OPEN,false);
    }

    @Override
    public List<Poll> findAllOpenPrivatePolls() {
        return pollRepository.findAllByActiveStatusAndIsPrivate(PollStatus.OPEN, true);
    }
}
