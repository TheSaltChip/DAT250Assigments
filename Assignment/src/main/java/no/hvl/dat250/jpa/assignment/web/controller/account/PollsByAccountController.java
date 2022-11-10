package no.hvl.dat250.jpa.assignment.web.controller.account;

import no.hvl.dat250.jpa.assignment.authenticationfacade.AuthenticationFacade;
import no.hvl.dat250.jpa.assignment.models.poll.Poll;
import no.hvl.dat250.jpa.assignment.models.user.User;
import no.hvl.dat250.jpa.assignment.service.poll.PollService;
import no.hvl.dat250.jpa.assignment.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PollsByAccountController {
    private final UserService userService;
    private final PollService pollService;
    private final AuthenticationFacade authenticationFacade;


    @Autowired
    public PollsByAccountController(UserService userService, PollService pollService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.pollService = pollService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(value = "account/polls")
    public String showPolls(Model model){
        Authentication authentication = authenticationFacade.getAuthentication();

        if(authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        User u = userService.getUserByUsername(authentication.getName());

        List<Poll> polls = pollService.findAllPollsByOwner(u);

        model.addAttribute("polls", polls);

        return "poll/mypolls";
    }
}
