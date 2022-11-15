package no.hvl.dat250.assignment.api;

import no.hvl.dat250.assignment.api.pojo.DevicePoll;
import no.hvl.dat250.assignment.api.pojo.DeviceVotes;
import no.hvl.dat250.assignment.api.pojo.Time;
import no.hvl.dat250.assignment.api.pojo.UserVote;
import no.hvl.dat250.assignment.models.poll.Poll;
import no.hvl.dat250.assignment.models.poll.TimeLimitPoll;
import no.hvl.dat250.assignment.models.user.User;
import no.hvl.dat250.assignment.service.poll.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PollRestController {

    private final PollService pollService;

    @Autowired
    public PollRestController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping(value = "/poll/connect/{code}")
    public DevicePoll connectDeviceToPoll(@PathVariable Integer code) {
        Poll p = pollService.getPollByCode(code);
        DevicePoll dp = new DevicePoll(p);
        pollService.createDeviceVote(dp.getIdentifier(), p.getId());
        return dp;
    }

    @GetMapping(value = "/polls")
    public List<Poll> findAllPolls() {
        return pollService.findAllPolls();
    }

    @GetMapping(value = "/poll/{pollId}")
    public Poll findById(@PathVariable Long pollId) {
        return pollService.findById(pollId);
    }

    @GetMapping(value = "/poll/owner/{pollId}")
    public User getOwner(@PathVariable Long pollId) {
        return pollService.getOwner(pollId);
    }

    @PutMapping(value = "/poll/vote/user/{pollId}")
    public Poll updateVote(@RequestBody UserVote test, @PathVariable Long pollId) {
        return pollService.updateUserVote(test.isVote(), test.getUsername(), pollId);
    }

    @PostMapping(value = "/poll/vote/device")
    public ResponseEntity<HttpStatus> updateVote(@RequestBody DeviceVotes deviceVotes) {
        pollService.updateDeviceVote(deviceVotes.getDeviceId(), deviceVotes.getYes(), deviceVotes.getNo(), deviceVotes.getPollId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(value = "/poll/update")
    public void updatePoll(@RequestBody Poll poll) {
        pollService.updatePoll(poll);
    }

    @PutMapping(value = "/poll/close/{pollId}")
    public Poll closePoll(@PathVariable Long pollId) {
        return pollService.closePoll(pollId);
    }

    @PutMapping(value = "/poll/open/{pollId}")
    public Poll openPoll(@PathVariable Long pollId) {
        return pollService.openPoll(pollId);
    }

    @PutMapping(value = "/poll/time/{pollId}")
    public Poll updateTime(@PathVariable Long pollId, @RequestBody Time date) {
        return pollService.updateTime(pollId, date.getStartDate(), date.getEndDate());
    }

    @PostMapping(value = "/poll")
    public Poll createPoll(@RequestBody Poll poll) {
        return pollService.createPoll(poll);
    }

    @PostMapping(value = "/timedPoll")
    public TimeLimitPoll createTimeLimitPoll(@RequestBody TimeLimitPoll poll) {
        return pollService.createTimeLimitPoll(poll);
    }

    @DeleteMapping(value = "/poll/{pollId}")
    public ResponseEntity<HttpStatus> deletePoll(@PathVariable Long pollId) {
        pollService.deletePoll(pollId);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
