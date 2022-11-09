package no.hvl.dat250.jpa.assignment.web.controller.poll;

import no.hvl.dat250.jpa.assignment.authentication.facade.AuthenticationFacade;
import no.hvl.dat250.jpa.assignment.models.poll.Poll;
import no.hvl.dat250.jpa.assignment.models.user.User;
import no.hvl.dat250.jpa.assignment.models.vote.UserVote;
import no.hvl.dat250.jpa.assignment.models.vote.AnonymousVote;
import no.hvl.dat250.jpa.assignment.models.vote.DeviceVote;
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
    private AuthenticationFacade authenticationFacade;
    private UserService userService;

    @Autowired
    public PollVoteController(PollService pollService, AuthenticationFacade authenticationFacade, UserService userService) {
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
            addUserVote(authentication,voteform,poll);
        }

        if (!(authentication instanceof AnonymousAuthenticationToken)){
            addUserVote(authentication,voteform,poll);
        }else{
            pollService.updateAnonymousVote(poll, voteform.isVote());
        }

        String string = "redirect:/poll/"+id+"/result";
        return string;
    }

    private void addUserVote(Authentication authentication,VoteForm voteform,Poll poll){
        User user = userService.getUserByUsername(authentication.getName());

        if(voteform.isVote()){
            pollService.updateUserVote(true, user.getUsername(), poll.getId());
        }else{
            pollService.updateUserVote(false, user.getUsername(), poll.getId());
        }
    }

}
