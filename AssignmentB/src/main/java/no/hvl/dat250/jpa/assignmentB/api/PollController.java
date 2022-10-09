package no.hvl.dat250.jpa.assignmentB.api;

import no.hvl.dat250.jpa.assignmentB.api.pojo.DeviceVotes;
import no.hvl.dat250.jpa.assignmentB.api.pojo.UserVote;
import no.hvl.dat250.jpa.assignmentB.models.User;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.TimeLimitPoll;
import no.hvl.dat250.jpa.assignmentB.service.poll.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class PollController {

    private final PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/poll/{pollId}")
    public Poll findById(@PathVariable Long pollId) {
        return pollService.findById(pollId);
    }

    @GetMapping("/user/{pollId}")
    public User getOwner(@PathVariable Long pollId) {
        return pollService.getOwner(pollId);
    }

    @PutMapping("/votePage/{pollId}")
    public Poll updateVote(@RequestBody UserVote test, @PathVariable Long pollId) {
        return pollService.updateVote(test.isVote(), test.getUsername(), pollId);
    }

    @PutMapping("/voteDevice/{pollId}")
    public Poll updateVote(@RequestBody DeviceVotes deviceVotes, @PathVariable Long pollId) {
        return pollService.updateVote(deviceVotes.getDeviceId(), deviceVotes.getYes(), deviceVotes.getNo(), pollId);
    }

    @PutMapping("/poll/update")
    public void updatePoll(@RequestBody Poll poll) {
        pollService.updatePoll(poll);
    }

    @PutMapping("/poll/close/{pollId}")
    public Poll closePoll(@PathVariable Long pollId) {
        return pollService.closePoll(pollId);
    }

    @PutMapping("/poll/open/{pollId}")
    public Poll openPoll(@PathVariable Long pollId) {
        return pollService.openPoll(pollId);
    }

    @PutMapping("/poll/time/{pollId}")
    public Poll updateTime(@PathVariable Long pollId, @RequestBody LocalDateTime startDate, @RequestBody LocalDateTime endDate) {
        return pollService.updateTime(pollId, startDate, endDate);
    }

    @PostMapping("poll")
    public Poll createPoll(@RequestBody Poll poll) {
        return pollService.createPoll(poll);
    }

    @PostMapping("timedPoll")
    public TimeLimitPoll createTimeLimitPoll(@RequestBody TimeLimitPoll poll) {
        return pollService.createTimeLimitPoll(poll);
    }

    @DeleteMapping("/deletePoll/{pollId}")
    public ResponseEntity<HttpStatus> deletePoll(@PathVariable Long pollId) {
        pollService.deletePoll(pollId);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
