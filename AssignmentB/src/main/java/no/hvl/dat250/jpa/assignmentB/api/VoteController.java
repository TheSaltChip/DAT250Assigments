package no.hvl.dat250.jpa.assignmentB.api;

import no.hvl.dat250.jpa.assignmentB.models.Vote;
import no.hvl.dat250.jpa.assignmentB.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping(value = "/votes")
    public ResponseEntity<List<Vote>> getAllVotes(){
        return ResponseEntity.ok(voteService.getAllVotes());
    }

    @GetMapping(value = "/votes/poll/{id}")
    public ResponseEntity<List<Vote>> getAllVotesFromPoll(@PathVariable Long id){
        return ResponseEntity.ok(voteService.getAllVotesFromPoll(id));
    }

    @GetMapping(value = "/votes/user/{username}")
    public ResponseEntity<List<Vote>> getAllVotesFromUser(@PathVariable String username){
        return ResponseEntity.ok(voteService.getAllVotesFromUser(username));
    }
}
