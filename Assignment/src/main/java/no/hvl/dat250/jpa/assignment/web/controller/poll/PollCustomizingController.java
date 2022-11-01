package no.hvl.dat250.jpa.assignment.web.controller.poll;

import no.hvl.dat250.jpa.assignment.authentication.facade.IAuthenticationFacade;
import no.hvl.dat250.jpa.assignment.models.Poll;
import no.hvl.dat250.jpa.assignment.models.User;
import no.hvl.dat250.jpa.assignment.service.poll.PollService;
import no.hvl.dat250.jpa.assignment.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PollCustomizingController {
    private final PollService pollService;
    private final UserService userService;
    private final IAuthenticationFacade authenticationFacade;

    @Autowired
    public PollCustomizingController(PollService pollService, UserService userService, IAuthenticationFacade authenticationFacade) {
        this.pollService = pollService;
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(value = "/poll/{id}")
    public String showPoll(@PathVariable Long id, Model model){
        Authentication authentication = authenticationFacade.getAuthentication();

        if(authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }

        User user = userService.getUserByUsername(authentication.getName());

        Poll poll = pollService.findById(id);

        if(!poll.getOwner().equals(user)){
            return "redirect:/";
        }

        model.addAttribute("poll", poll);

        return "poll/customizepoll";
    }
}
