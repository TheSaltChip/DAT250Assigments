package no.hvl.dat250.jpa.assignmentB.service.poll;

import no.hvl.dat250.jpa.assignmentB.models.*;
import no.hvl.dat250.jpa.assignmentB.repository.poll.PollRepository;
import no.hvl.dat250.jpa.assignmentB.repository.poll.TimeLimitPollRepository;
import no.hvl.dat250.jpa.assignmentB.repository.user.UserRepository;
import no.hvl.dat250.jpa.assignmentB.repository.vote.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public List<Poll> findAllPolls() {
        return pollRepository.findAll();
    }

    @Override
    public Poll findById(Long pollId) {
        return pollRepository.findById(pollId).orElseThrow();
    }


    @Override
    public User getOwner(Long pollId) {
        Poll p = pollRepository.findById(pollId).orElse(null);

        if (p == null) return null;

        return p.getOwner();
    }

    @Override
    public Poll updateVote(boolean vote, String username, Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        if (!p.getActive()) return p;

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
        if (!p.getActive()) return p;

        User d = userRepository.findById(deviceId).orElseThrow();

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

    private Poll findAndSetActive(Long pollId, boolean status) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        p.setActive(status);

        return pollRepository.save(p);
    }
}
