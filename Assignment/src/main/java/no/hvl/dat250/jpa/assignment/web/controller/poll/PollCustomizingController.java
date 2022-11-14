package no.hvl.dat250.jpa.assignment.web.controller.poll;

import no.hvl.dat250.jpa.assignment.authenticationfacade.AuthenticationFacade;
import no.hvl.dat250.jpa.assignment.models.poll.Poll;
import no.hvl.dat250.jpa.assignment.models.poll.PollStatus;
import no.hvl.dat250.jpa.assignment.models.user.User;
import no.hvl.dat250.jpa.assignment.service.poll.PollService;
import no.hvl.dat250.jpa.assignment.service.user.UserService;
import no.hvl.dat250.jpa.assignment.web.formobject.PollCustomizeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.NoSuchElementException;

@Controller
public class PollCustomizingController {
    private final PollService pollService;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public PollCustomizingController(PollService pollService, UserService userService, AuthenticationFacade authenticationFacade) {
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

        User user;

        try {
            user = userService.getUserByUsername(authentication.getName());
        } catch (NoSuchElementException e) {
            return "redirect:/login";
        }

        Poll poll;

        try {
            poll = pollService.findById(id);
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }

        if (!poll.getOwner().equals(user)) {
            return "redirect:/";
        }

        PollCustomizeForm pcf = new PollCustomizeForm(poll.getQuestion(), poll.getTheme(), poll.getIsPrivate(), poll.getActiveStatus(), poll.getCode());

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

        User user;

        try {
            user = userService.getUserByUsername(authentication.getName());
        } catch (NoSuchElementException e) {
            return "redirect:/login";
        }

        Poll poll;

        try {
            poll = pollService.findById(id);
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }

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
                poll.setQuestion(pollCustomizeForm.getQuestion().replaceAll("\\s+", " ").trim());
                poll.setTheme(pollCustomizeForm.getTheme().replaceAll("\\s+", " ").trim());
                poll.setIsPrivate(pollCustomizeForm.getIsPrivate());
                break;
        }

        try {
            pollService.updatePoll(poll);

            switch (pollCustomizeForm.getActiveStatus()) {
                case CLOSED:
                    break;
                case OPEN:
                    if (poll.getActiveStatus().equals(PollStatus.CLOSED))
                        pollService.openPoll(poll.getId());
                    break;
                case FINISHED:
                    if (poll.getActiveStatus().equals(PollStatus.OPEN))
                        pollService.closePoll(poll.getId());
                    break;
            }
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }

        return "redirect:/poll/" + poll.getId();
    }
}
