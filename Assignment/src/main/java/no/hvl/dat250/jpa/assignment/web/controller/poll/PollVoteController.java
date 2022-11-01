package no.hvl.dat250.jpa.assignment.web.controller.poll;

import no.hvl.dat250.jpa.assignment.authentication.facade.IAuthenticationFacade;
import no.hvl.dat250.jpa.assignment.models.Poll;
import no.hvl.dat250.jpa.assignment.models.User;
import no.hvl.dat250.jpa.assignment.models.Vote;
import no.hvl.dat250.jpa.assignment.service.poll.PollService;
import no.hvl.dat250.jpa.assignment.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PollVoteController {

    private PollService pollService;
    private IAuthenticationFacade authenticationFacade;
    private UserService userService;

    @Autowired
    public PollVoteController(PollService pollService, IAuthenticationFacade authenticationFacade, UserService userService) {
        this.authenticationFacade = authenticationFacade;
        this.pollService = pollService;
        this.userService = userService;
    }

    @GetMapping(value = "/poll/{id}")
    public String votePage(@PathVariable Long pollId, Model model){
        Poll poll = pollService.findById(pollId);
        if(poll.getIsPrivate()){
            Authentication authentication = authenticationFacade.getAuthentication();
            if (authentication instanceof AnonymousAuthenticationToken) {
                return "login";
            }
        }
        model.addAttribute("poll",poll);
        return "poll/pollvote";
    }

    @PostMapping(value = "/poll/{id}")
    public String voteRegister(@PathVariable Long pollId, Model model){
        Poll poll = pollService.findById(pollId);
        Authentication authentication = authenticationFacade.getAuthentication();
        if(poll.getIsPrivate()){
            if (authentication instanceof AnonymousAuthenticationToken) {
                return "login";
            }
        }
        User user = userService.getUserByUsername(authentication.getName());
        Vote vote = new Vote(user,poll,0,0);
        if()

        //Somehow get to the controller
        return "";
    }

}
