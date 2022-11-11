package no.hvl.dat250.jpa.assignment.web.controller.admin;

import no.hvl.dat250.jpa.assignment.authenticationfacade.AuthenticationFacade;
import no.hvl.dat250.jpa.assignment.models.poll.Poll;
import no.hvl.dat250.jpa.assignment.models.poll.PollStatus;
import no.hvl.dat250.jpa.assignment.models.user.User;
import no.hvl.dat250.jpa.assignment.service.poll.PollService;
import no.hvl.dat250.jpa.assignment.service.user.UserService;
import no.hvl.dat250.jpa.assignment.web.formobject.PollCustomizeForm;
import no.hvl.dat250.jpa.assignment.web.formobject.UserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import static no.hvl.dat250.jpa.assignment.models.poll.PollStatus.*;

@Controller
public class AdminController {
    private final UserService userService;
    private final PollService pollService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public AdminController(UserService userService, PollService pollService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.pollService = pollService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(value = "/admin/users")
    public String showUsers(Model model) {
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);

        return "admin/user/adminusers";
    }

    @GetMapping(value = "/admin/user/{username}")
    public String showUser(@PathVariable String username, Model model) {
        User user = userService.getUserByUsername(username);

        UserUpdateForm uuf = new UserUpdateForm(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail());

        model.addAttribute("user", uuf);

        return "admin/user/adminuseredit";
    }

    @PutMapping(value = "/admin/user/{username}")
    public String editUser(@PathVariable String username, UserUpdateForm uuf) {
        userService.updateUser(username, uuf);

        return "redirect:/admin/user/" + username;
    }

    @DeleteMapping(value = "/admin/user/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUser(username);

        return "redirect:/admin/users";
    }

    @GetMapping(value = "/admin/polls")
    public String showPolls(Model model) {
        List<Poll> polls = pollService.findAllPolls();

        model.addAttribute("polls", polls);

        return "admin/poll/adminpolls";
    }

    @GetMapping(value = "/admin/poll/{id}")
    public String showPoll(@PathVariable Long id, Model model) {
        Poll poll = pollService.findById(id);

        PollCustomizeForm pcf = new PollCustomizeForm(poll.getQuestion(), poll.getTheme(), poll.getIsPrivate(), poll.getActiveStatus(), poll.getCode());

        model.addAttribute("poll", pcf);
        model.addAttribute("pollId", poll.getId());


        return "admin/poll/adminpolledit";
    }

    @PutMapping(value = "/admin/poll/{id}")
    public String editPoll(@PathVariable Long id, PollCustomizeForm pcf) {
        System.out.println("put");
        Poll poll = pollService.findById(id);

        switch (poll.getActiveStatus()) {
            case FINISHED:
                return "redirect:/admin/poll/" + poll.getId();
            case OPEN:
            case CLOSED:
                poll.setQuestion(pcf.getQuestion());
                poll.setTheme(pcf.getTheme());
                poll.setIsPrivate(pcf.getIsPrivate());
                break;
        }

        pollService.updatePoll(poll);

        switch (pcf.getActiveStatus()) {
            case CLOSED:
                break;
            case OPEN:
                if (poll.getActiveStatus().equals(CLOSED))
                    pollService.openPoll(poll.getId());
                break;
            case FINISHED:
                if (poll.getActiveStatus().equals(OPEN))
                    pollService.closePoll(poll.getId());
                break;
        }

        return "redirect:/admin/poll/" + poll.getId();
    }

    @DeleteMapping(value = "/admin/poll/{id}")
    public String deletePoll(@PathVariable Long id) {
        System.out.println("delete");
        pollService.deletePoll(id);

        return "redirect:/admin/polls";
    }
}
