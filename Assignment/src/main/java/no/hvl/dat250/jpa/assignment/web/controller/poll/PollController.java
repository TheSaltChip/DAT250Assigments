package no.hvl.dat250.jpa.assignment.web.controller.poll;

import no.hvl.dat250.jpa.assignment.authentication.facade.IAuthenticationFacade;
import no.hvl.dat250.jpa.assignment.models.Poll;
import no.hvl.dat250.jpa.assignment.service.poll.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PollController {
    private final PollService pollService;
    private final IAuthenticationFacade authenticationFacade;

    @Autowired
    public PollController(PollService pollService, IAuthenticationFacade authenticationFacade) {
        this.pollService = pollService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        Authentication authentication = authenticationFacade.getAuthentication();

        System.out.println(authentication.getName());

        List<Poll> polls = authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)
                ? pollService.findAllOpenPolls()
                : pollService.findAllOpenPublicPolls();

        polls.forEach(System.out::println);

        model.addAttribute("polls", polls);

        return "index";
    }

    // TODO Link form with this
    @PostMapping(value="/poll")
    public String CreatePoll(){

        return "";
    }
}
