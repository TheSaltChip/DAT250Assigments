package no.hvl.dat250.jpa.assignment.web.controller.poll;

import no.hvl.dat250.jpa.assignment.authentication.facade.IAuthenticationFacade;
import no.hvl.dat250.jpa.assignment.models.Poll;
import no.hvl.dat250.jpa.assignment.models.User;
import no.hvl.dat250.jpa.assignment.service.poll.PollService;
import no.hvl.dat250.jpa.assignment.service.user.UserService;
import no.hvl.dat250.jpa.assignment.web.formObject.PollCustomizeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

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
    public String showPoll(@PathVariable Long id, Model model) {
        Authentication authentication = authenticationFacade.getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        User user = userService.getUserByUsername(authentication.getName());

        Poll poll = pollService.findById(id);

        if (!poll.getOwner().equals(user)) {
            return "redirect:/";
        }

        PollCustomizeForm pcf = new PollCustomizeForm(poll.getQuestion(), poll.getTheme(), poll.getIsPrivate(), poll.getActiveStatus());

        model.addAttribute("pollCustomizeForm", pcf);
        model.addAttribute("pollId", poll.getId());

        return "poll/customizepoll";
    }

    @PutMapping(value = "/poll/{id}")
    public String updatePoll(@PathVariable Long id, PollCustomizeForm pollCustomizeForm) {
        Authentication authentication = authenticationFacade.getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        User user = userService.getUserByUsername(authentication.getName());

        Poll poll = pollService.findById(id);

        if (!poll.getOwner().equals(user)) {
            return "redirect:/";
        }

        switch (poll.getActiveStatus()) {
            case FINISHED:
                return "redirect:/poll/" + poll.getId();
            case OPEN:
                poll.setIsPrivate(pollCustomizeForm.getIsPrivate());
                break;
            case CLOSED:
                poll.setQuestion(pollCustomizeForm.getQuestion());
                poll.setTheme(pollCustomizeForm.getTheme());
                poll.setIsPrivate(pollCustomizeForm.getIsPrivate());
                break;
        }

        poll.setActiveStatus(pollCustomizeForm.getActiveStatus());

        pollService.updatePoll(poll);

        return "redirect:/poll/" + poll.getId();
    }
}
