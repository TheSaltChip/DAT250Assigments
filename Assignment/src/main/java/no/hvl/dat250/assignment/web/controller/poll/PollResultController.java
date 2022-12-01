package no.hvl.dat250.assignment.web.controller.poll;

import no.hvl.dat250.assignment.web.authenticationfacade.AuthenticationFacade;
import no.hvl.dat250.assignment.persistence.models.poll.Poll;
import no.hvl.dat250.assignment.service.poll.PollService;
import no.hvl.dat250.assignment.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.NoSuchElementException;

@Controller
public class PollResultController {
    private final PollService pollService;
    private final VoteService voteService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public PollResultController(PollService pollService, VoteService voteService, AuthenticationFacade authenticationFacade) {
        this.pollService = pollService;
        this.voteService = voteService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(value = "/poll/{id}/result")
    public String showPollResult(@PathVariable Long id, Model model) {
        Poll poll;

        try {
            poll = pollService.findById(id);
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }

        Authentication authentication = authenticationFacade.getAuthentication();

        if (poll.getIsPrivate() && (authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        String username = authentication.getName();

        if (!(authentication instanceof AnonymousAuthenticationToken) && !voteService.hasUserVotedInPoll(username, id)
                && !poll.getOwner().getUsername().equals(authentication.getName())) {
            return "redirect:/poll/" + id + "/vote";
        }

        model.addAttribute("poll", poll);

        return "poll/pollresult";
    }
}
