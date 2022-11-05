package no.hvl.dat250.jpa.assignment.web.controller.poll;

import no.hvl.dat250.jpa.assignment.authentication.facade.IAuthenticationFacade;
import no.hvl.dat250.jpa.assignment.models.Poll;
import no.hvl.dat250.jpa.assignment.models.User;
import no.hvl.dat250.jpa.assignment.models.Vote;
import no.hvl.dat250.jpa.assignment.service.poll.PollService;
import no.hvl.dat250.jpa.assignment.service.user.UserService;
import no.hvl.dat250.jpa.assignment.web.formObject.VoteForm;
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

    @GetMapping(value = "/poll/{id}/vote")
    public String votePage(@PathVariable Long id, Model model){
        Poll poll = pollService.findById(id);
        if(poll.getIsPrivate()) {
            Authentication authentication = authenticationFacade.getAuthentication();
            if (authentication instanceof AnonymousAuthenticationToken) {
                return "redirect:/login";
            }
        }
        model.addAttribute("poll",poll);
        model.addAttribute("voteform",new VoteForm());
        return "poll/pollvote";
    }

    @PostMapping(value = "/poll/{id}/vote")
    public String voteRegister(@PathVariable Long id, VoteForm voteform){
        Poll poll = pollService.findById(id);
        Authentication authentication = authenticationFacade.getAuthentication();
        if(poll.getIsPrivate()){
            if (authentication instanceof AnonymousAuthenticationToken) {
                return "redirect:login";
            }
        }
        User user = userService.getUserByUsername(authentication.getName());
        Vote vote = new Vote(user,poll,0,0);

        if(voteform.isVote()){
            vote.setYesVotes(1);
        }else{
            vote.setNoVotes(1);
        }

        poll.getVotes().add(vote);

        String string = "redirect:/poll/"+id+"/result";
        return string;
    }

}
