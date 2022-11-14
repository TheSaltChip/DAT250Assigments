package no.hvl.dat250.jpa.assignment.service.poll;

import no.hvl.dat250.jpa.assignment.message.MessagingClient;
import no.hvl.dat250.jpa.assignment.message.PollAnalytic;
import no.hvl.dat250.jpa.assignment.models.poll.Poll;
import no.hvl.dat250.jpa.assignment.models.poll.PollStatus;
import no.hvl.dat250.jpa.assignment.models.poll.TimeLimitPoll;
import no.hvl.dat250.jpa.assignment.models.user.Role;
import no.hvl.dat250.jpa.assignment.models.user.User;
import no.hvl.dat250.jpa.assignment.models.vote.AnonymousVote;
import no.hvl.dat250.jpa.assignment.models.vote.DeviceVote;
import no.hvl.dat250.jpa.assignment.models.vote.UserVote;
import no.hvl.dat250.jpa.assignment.models.vote.UserVoteId;
import no.hvl.dat250.jpa.assignment.repository.poll.PollRepository;
import no.hvl.dat250.jpa.assignment.repository.poll.TimeLimitPollRepository;
import no.hvl.dat250.jpa.assignment.repository.user.UserRepository;
import no.hvl.dat250.jpa.assignment.repository.vote.AnonymousVoteRepository;
import no.hvl.dat250.jpa.assignment.repository.vote.DeviceVoteRepository;
import no.hvl.dat250.jpa.assignment.repository.vote.UserVoteRepository;
import no.hvl.dat250.jpa.assignment.service.dweet.DweetService;
import no.hvl.dat250.jpa.assignment.web.formobject.PollCustomizeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PollServiceImpl implements PollService {
    private final SecureRandom random;
    private final MessagingClient messagingClient;
    private final PollRepository pollRepository;
    private final TimeLimitPollRepository timeLimitPollRepository;
    private final UserRepository userRepository;
    private final UserVoteRepository userVoteRepository;
    private final DeviceVoteRepository deviceVoteRepository;
    private final AnonymousVoteRepository anonymousVoteRepository;
    private final DweetService dweetService;

    @Autowired
    public PollServiceImpl(MessagingClient messagingClient, PollRepository pollRepository, TimeLimitPollRepository timeLimitPollRepository, UserRepository userRepository, UserVoteRepository userVoteRepository, DeviceVoteRepository deviceVoteRepository, AnonymousVoteRepository anonymousVoteRepository, DweetService dweetService) {
        this.messagingClient = messagingClient;
        this.pollRepository = pollRepository;
        this.timeLimitPollRepository = timeLimitPollRepository;
        this.userRepository = userRepository;
        this.userVoteRepository = userVoteRepository;
        this.deviceVoteRepository = deviceVoteRepository;
        this.anonymousVoteRepository = anonymousVoteRepository;
        this.dweetService = dweetService;

        this.random = new SecureRandom(ByteBuffer.allocate(4).putInt(1337).array());
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
    public Poll getPollByCode(Integer code) {
        return pollRepository.findByCode(code).orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional
    public Poll updateUserVote(boolean vote, String username, Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        if (!p.getActiveStatus().equals(PollStatus.OPEN)) return p;

        User u = userRepository.findById(username).orElseThrow();

        if (!u.getRole().equals(Role.Regular)) {
            return p;
        }

        Optional<UserVote> oV = userVoteRepository.findById(new UserVoteId(username, pollId));

        if (vote) {
            p.incYesVotes();
        } else {
            p.incNoVotes();
        }

        if (oV.isPresent()) {
            UserVote v = oV.get();
            v.setYesVotes(vote ? 1 : 0);
            v.setNoVotes(vote ? 0 : 1);

            userVoteRepository.save(v);

            return pollRepository.save(p);
        }

        UserVote v = new UserVote(username, pollId, vote ? 1 : 0, vote ? 0 : 1);

        userVoteRepository.save(v);

        return pollRepository.save(p);
    }

    @Override
    @Transactional
    public void createDeviceVote(UUID deviceId, Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        if (!p.getActiveStatus().equals(PollStatus.OPEN)) return;

        DeviceVote v = new DeviceVote(deviceId, p, 0, 0);

        deviceVoteRepository.save(v);
    }

    @Override
    @Transactional
    public void updateDeviceVote(UUID deviceId, int yes, int no, Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        // Custom response should be given here telling that the poll is closed
        if (!p.getActiveStatus().equals(PollStatus.OPEN)) return;

        Optional<DeviceVote> oVote = deviceVoteRepository.findById(deviceId);

        if (oVote.isEmpty()) {
            return;
        }

        DeviceVote v = oVote.get();

        p.addYesVotes(yes);
        p.addNoVotes(no);

        v.setNoVotes(v.getNoVotes() + no);
        v.setYesVotes(v.getYesVotes() + yes);

        deviceVoteRepository.save(v);

        pollRepository.save(p);
    }

    @Override
    @Transactional
    public void createAnonymousVote(Poll poll, boolean vote) {
        AnonymousVote anonymousVote = new AnonymousVote(poll, vote);

        if (vote) {
            poll.incYesVotes();
        } else {
            poll.incNoVotes();
        }

        anonymousVoteRepository.save(anonymousVote);
    }

    @Override
    @Transactional
    public void updatePoll(Poll poll) {
        Poll updatedPoll = pollRepository.findById(poll.getId()).orElseThrow();

        updatedPoll.setQuestion(poll.getQuestion());
        updatedPoll.setTheme(poll.getTheme());
        updatedPoll.setIsPrivate(poll.getIsPrivate());

        pollRepository.save(updatedPoll);
    }

    @Override
    @Transactional
    public void updatePoll(Long id, PollCustomizeForm pcf) {
        Poll updatedPoll = pollRepository.findById(id).orElseThrow();

        updatedPoll.setQuestion(pcf.getQuestion());
        updatedPoll.setTheme(pcf.getTheme());
        updatedPoll.setIsPrivate(pcf.getIsPrivate());

        pollRepository.save(updatedPoll);
    }


    @Override
    @Transactional
    public Poll closePoll(Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        p.setActiveStatusToFinished();
        p.setCode(0);

        // Find by poll id
        List<DeviceVote> dv = deviceVoteRepository.findAllByPoll_Id(pollId);

        List<UserVote> uv = userVoteRepository.findAllByPoll_Id(pollId);

        List<AnonymousVote> av = anonymousVoteRepository.findAllByPoll_Id(pollId);

        PollAnalytic pa = new PollAnalytic(
                p.getQuestion(), p.getTheme(),
                p.getOwner().getUsername(),
                p.getYesVotes(), p.getNoVotes(),
                dv.stream().map(d -> d.getYesVotes() + d.getNoVotes()).reduce(Integer::sum).orElse(0),
                uv.stream().map(d -> d.getYesVotes() + d.getNoVotes()).reduce(Integer::sum).orElse(0),
                av.stream().map(d -> d.getYesVotes() + d.getNoVotes()).reduce(Integer::sum).orElse(0));

        messagingClient.publishMessage(pa);

        dweetService.pollFinished(p);

        return pollRepository.save(p);
    }


    @Override
    @Transactional
    public Poll openPoll(Long pollId) {
        Poll p = pollRepository.findById(pollId).orElseThrow();

        p.setActiveStatusToOpen();

        int code = random.nextInt(1_000_000);

        while (code < 10_000
                || (code > 10_000 && pollRepository.existsPollByCode(code)))
            code = random.nextInt(1_000_000);

        p.setCode(code);

        dweetService.pollOpened(p);

        return pollRepository.save(p);
    }

    @Override
    @Transactional
    public Poll updateTime(Long pollId, LocalDateTime startDate, LocalDateTime endDate) {
        TimeLimitPoll tlp = timeLimitPollRepository.findById(pollId).orElseThrow();

        tlp.setStartDate(startDate);
        tlp.setEndDate(endDate);

        return timeLimitPollRepository.save(tlp);
    }

    @Override
    @Transactional
    public Poll createPoll(Poll poll) {
        poll.setCode(0);
        return pollRepository.save(poll);
    }

    @Override
    @Transactional
    public TimeLimitPoll createTimeLimitPoll(TimeLimitPoll poll) {
        return timeLimitPollRepository.save(poll);
    }

    @Override
    @Transactional
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
        return pollRepository.findAllByActiveStatusAndIsPrivate(PollStatus.OPEN, false);
    }

    @Override
    public List<Poll> findAllOpenPrivatePolls() {
        return pollRepository.findAllByActiveStatusAndIsPrivate(PollStatus.OPEN, true);
    }

    @Override
    public List<Poll> findAllPollsByOwner(User owner) {
        return pollRepository.findAllByOwner(owner);
    }
}
