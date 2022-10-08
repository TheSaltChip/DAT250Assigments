package no.hvl.dat250.jpa.assignmentB.api;

import no.hvl.dat250.jpa.assignmentB.dao.ClientDao;
import no.hvl.dat250.jpa.assignmentB.dao.PollDao;
import no.hvl.dat250.jpa.assignmentB.models.User;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.TimeLimitPoll;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class PollController {

    private final PollDao pollDao = new PollDao();

    /* public PollController(PollDao pollDao) {
        this.pollDao = pollDao;
    } */

    @GetMapping("/poll/{pollId}")
    public Poll findById(@PathVariable int pollId){
        return pollDao.findById(pollId);
    }

    @GetMapping("/user/{pollId}")
    public User getOwner(@PathVariable int pollId) {
        return pollDao.getOwner(pollId);
    }

    @PutMapping("/votePage/{pollId}")
    public Poll updateVote(@RequestBody boolean vote,@PathVariable int pollId){
        return pollDao.updateVote(vote,pollId);
    }

    @PutMapping("/voteDevice/{pollId}")
    public Poll updateVote(@RequestBody int yes,@RequestBody int no,@PathVariable int pollId) {
        return pollDao.updateVote(yes,no,pollId);
    }

    @PutMapping("/poll/update")
    public void updatePoll(@RequestBody Poll poll) {
        pollDao.updatePoll(Integer.parseInt(""+poll.getId()),poll.getName(),poll.getTheme());
    }

    @PutMapping("/close/{pollId}")
    public Poll closePoll(@PathVariable int pollId) {
        return pollDao.closePoll(pollId);
    }

    @PutMapping("/open/{pollId}")
    public Poll openPoll(@PathVariable int pollId) {
        return pollDao.openPoll(pollId);
    }

    @PutMapping("/poll/time/{pollId}")
    public Poll updateTime(@PathVariable int pollId,@RequestBody LocalDateTime startDate,@RequestBody LocalDateTime endDate) {
       return pollDao.updateTime(pollId,startDate,endDate);
    }

    @PostMapping("poll")
    public Poll createPoll(@RequestBody Poll poll) {
        return pollDao.createPoll(poll.getName(),poll.getTheme(), poll.getPrivate(),poll.getCreatedDate(),poll.getOwner());
    }

    @PostMapping("timedPoll")
    public TimeLimitPoll createTimeLimitPoll(@RequestBody TimeLimitPoll poll) {
        return pollDao.createTimeLimitPoll(poll.getName(),poll.getTheme(),poll.getPrivate(),poll.getCreatedDate(),poll.getOwner(),poll.getStartDate(),poll.getEndDate());
    }

    @DeleteMapping("/deletePoll/{pollId}")
    public Poll deletePoll(@PathVariable int pollId) {
        return pollDao.deletePoll(pollId);
    }
}
