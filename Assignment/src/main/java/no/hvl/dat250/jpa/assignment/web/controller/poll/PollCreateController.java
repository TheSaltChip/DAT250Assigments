package no.hvl.dat250.jpa.assignment.web.controller.poll;

import no.hvl.dat250.jpa.assignment.authentication.facade.IAuthenticationFacade;
import no.hvl.dat250.jpa.assignment.models.Poll;
import no.hvl.dat250.jpa.assignment.models.User;
import no.hvl.dat250.jpa.assignment.service.poll.PollService;
import no.hvl.dat250.jpa.assignment.service.user.UserService;
import no.hvl.dat250.jpa.assignment.web.formObject.PollForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PollCreateController {
    private final PollService pollService;
    private final UserService userService;
    private final IAuthenticationFacade authenticationFacade;

    @Autowired
    public PollCreateController(PollService pollService, UserService userService, IAuthenticationFacade authenticationFacade) {
        this.pollService = pollService;
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(value = "/poll/create")
    public String showForm(PollForm pollForm) {
        return "poll/pollcreation";
    }

    @PostMapping(value = "/poll/create")
    public String validateInput(@Valid PollForm pollForm, BindingResult bindResult) {
        Authentication authentication = authenticationFacade.getAuthentication();

        if (bindResult.hasErrors() || authentication instanceof AnonymousAuthenticationToken) {
            return "poll/pollcreation";
        }


        User u = userService.getUserByUsername(authentication.getName());

        Poll poll = new Poll(pollForm.getQuestion(), pollForm.getTheme(), pollForm.getIsPrivate(), u);

        return "redirect:/poll/" + poll.getId();
    }
}
