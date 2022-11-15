package no.hvl.dat250.assignment.web.controller.index;

import no.hvl.dat250.assignment.authenticationfacade.AuthenticationFacade;
import no.hvl.dat250.assignment.models.poll.Poll;
import no.hvl.dat250.assignment.service.poll.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    private final PollService pollService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public IndexController(PollService pollService, AuthenticationFacade authenticationFacade) {
        this.pollService = pollService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        Authentication authentication = authenticationFacade.getAuthentication();

        List<Poll> polls = authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)
                ? pollService.findAllOpenPolls()
                : pollService.findAllOpenPublicPolls();

        model.addAttribute("polls", polls);

        return "index";
    }
}
