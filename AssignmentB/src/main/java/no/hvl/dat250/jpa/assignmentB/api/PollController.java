package no.hvl.dat250.jpa.assignmentB.api;

import no.hvl.dat250.jpa.assignmentB.models.User;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.TimeLimitPoll;
import no.hvl.dat250.jpa.assignmentB.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class PollController {
/*
    private final PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/poll/{pollId}")
    public Poll findById(@PathVariable int pollId){
        return pollService.findById(pollId);
    }

    @GetMapping("/user/{pollId}")
    public User getOwner(@PathVariable int pollId) {
        return pollService.getOwner(pollId);
    }

    @PutMapping("/votePage/{pollId}")
    public Poll updateVote(@RequestBody boolean vote,@PathVariable int pollId){
        return pollService.updateVote(vote,pollId);
    }

    @PutMapping("/voteDevice/{pollId}")
    public Poll updateVote(@RequestBody int yes,@RequestBody int no,@PathVariable int pollId) {
        return pollService.updateVote(yes,no,pollId);
    }

    @PutMapping("/poll/update")
    public void updatePoll(@RequestBody Poll poll) {
        pollService.updatePoll(Integer.parseInt(""+poll.getId()),poll.getName(),poll.getTheme());
    }

    @PutMapping("/close/{pollId}")
    public Poll closePoll(@PathVariable int pollId) {
        return pollService.closePoll(pollId);
    }

    @PutMapping("/open/{pollId}")
    public Poll openPoll(@PathVariable int pollId) {
        return pollService.openPoll(pollId);
    }

    @PutMapping("/poll/time/{pollId}")
    public Poll updateTime(@PathVariable int pollId,@RequestBody LocalDateTime startDate,@RequestBody LocalDateTime endDate) {
       return pollService.updateTime(pollId,startDate,endDate);
    }

    @PostMapping("poll")
    public Poll createPoll(@RequestBody Poll poll) {
        return pollService.createPoll(poll.getName(),poll.getTheme(), poll.getPrivate(),poll.getCreatedDate(),poll.getOwner());
    }

    @PostMapping("timedPoll")
    public TimeLimitPoll createTimeLimitPoll(@RequestBody TimeLimitPoll poll) {
        return pollService.createTimeLimitPoll(poll.getName(),poll.getTheme(),poll.getPrivate(),poll.getCreatedDate(),poll.getOwner(),poll.getStartDate(),poll.getEndDate());
    }

    @DeleteMapping("/deletePoll/{pollId}")
    public Poll deletePoll(@PathVariable int pollId) {
        return pollService.deletePoll(pollId);
    }*/
}
