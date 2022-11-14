package no.hvl.dat250.jpa.assignment.api;

import no.hvl.dat250.jpa.assignment.models.vote.UserVote;
import no.hvl.dat250.jpa.assignment.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VoteRestController {

    private final VoteService voteService;

    @Autowired
    public VoteRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping(value = "/votes")
    public ResponseEntity<List<UserVote>> getAllVotes() {
        return ResponseEntity.ok(voteService.getAllVotes());
    }

    @GetMapping(value = "/votes/poll/{id}")
    public ResponseEntity<List<UserVote>> getAllVotesFromPoll(@PathVariable Long id) {
        return ResponseEntity.ok(voteService.getAllVotesFromPoll(id));
    }

    @GetMapping(value = "/votes/user/{username}")
    public ResponseEntity<List<UserVote>> getAllVotesFromUser(@PathVariable String username) {
        return ResponseEntity.ok(voteService.getAllVotesFromUser(username));
    }
}
