package no.hvl.dat250.assignment.web.controller.poll;

import no.hvl.dat250.assignment.models.poll.Poll;
import no.hvl.dat250.assignment.authenticationfacade.AuthenticationFacade;
import no.hvl.dat250.assignment.models.user.User;
import no.hvl.dat250.assignment.service.poll.PollService;
import no.hvl.dat250.assignment.service.user.UserService;
import no.hvl.dat250.assignment.service.vote.VoteService;
import no.hvl.dat250.assignment.web.formObject.VoteForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.NoSuchElementException;

@Controller
public class PollVoteController {
    private final PollService pollService;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;
    private final VoteService voteService;

    @Autowired
    public PollVoteController(PollService pollService, AuthenticationFacade authenticationFacade, UserService userService, VoteService voteService) {
        this.authenticationFacade = authenticationFacade;
        this.pollService = pollService;
        this.userService = userService;
        this.voteService = voteService;
    }

    @GetMapping(value = "/poll/{id}/vote")
    public String votePage(@PathVariable Long id, Model model) {
        Poll poll;

        try {
            poll = pollService.findById(id);
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }

        Authentication authentication = authenticationFacade.getAuthentication();

        if (poll.getIsPrivate()) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return "redirect:/login";
            }
        }
        if (!(authentication instanceof AnonymousAuthenticationToken) &&
                voteService.hasUserVotedInPoll(authentication.getName(), id)) {
            return "redirect:result";
        }
        model.addAttribute("poll", poll);
        model.addAttribute("voteform", new VoteForm());
        return "poll/pollvote";
    }

    @PostMapping(value = "/poll/{id}/vote")
    public String voteRegister(@PathVariable Long id, VoteForm voteform) {
        Poll poll;

        try {
            poll = pollService.findById(id);
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }

        Authentication authentication = authenticationFacade.getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user;

            try {
                user = userService.getUserByUsername(authentication.getName());
                pollService.updateUserVote(voteform.isVote(), user.getUsername(), id);
            } catch (NoSuchElementException e) {
                return "redirect:/login";
            }

            return "redirect:result";
        }

        if (poll.getIsPrivate()) {
            return "redirect:/login";
        }

        try {
            pollService.createAnonymousVote(poll, voteform.isVote());
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }

        return "redirect:result";
    }

}
