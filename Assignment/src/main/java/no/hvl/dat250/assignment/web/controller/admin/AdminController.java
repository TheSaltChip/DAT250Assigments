package no.hvl.dat250.assignment.web.controller.admin;

import no.hvl.dat250.assignment.models.poll.Poll;
import no.hvl.dat250.assignment.models.user.User;
import no.hvl.dat250.assignment.service.poll.PollService;
import no.hvl.dat250.assignment.service.user.UserService;
import no.hvl.dat250.assignment.web.formObject.PollCustomizeForm;
import no.hvl.dat250.assignment.web.formObject.UserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.NoSuchElementException;

import static no.hvl.dat250.assignment.models.poll.PollStatus.CLOSED;
import static no.hvl.dat250.assignment.models.poll.PollStatus.OPEN;

@Controller
public class AdminController {
    private final UserService userService;
    private final PollService pollService;

    @Autowired
    public AdminController(UserService userService, PollService pollService) {
        this.userService = userService;
        this.pollService = pollService;
    }

    @GetMapping(value = "/admin/users")
    public String showUsers(Model model) {
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);

        return "admin/user/adminusers";
    }

    @GetMapping(value = "/admin/user/{username}")
    public String showUser(@PathVariable String username, Model model) {
        try {
            User user = userService.getUserByUsername(username);

            UserUpdateForm uuf = new UserUpdateForm(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail());

            model.addAttribute("user", uuf);

            return "admin/user/adminuseredit";
        } catch (NoSuchElementException e) {
            return "redirect:/admin/users";
        }
    }

    @PutMapping(value = "/admin/user/{username}")
    public String editUser(@PathVariable String username, UserUpdateForm uuf) {
        try {
            userService.updateUser(username, uuf);

            return "redirect:/admin/user/" + username;
        } catch (NoSuchElementException e) {
            return "redirect:/admin/users";
        }
    }

    @DeleteMapping(value = "/admin/user/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUser(username);

        return "redirect:/admin/users";
    }

    @GetMapping(value = "/admin/polls")
    public String showPolls(Model model) {
        try {
            List<Poll> polls = pollService.findAllPolls();

            model.addAttribute("polls", polls);

            return "admin/poll/adminpolls";
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }
    }

    @GetMapping(value = "/admin/poll/{id}")
    public String showPoll(@PathVariable Long id, Model model) {
        try {
            Poll poll = pollService.findById(id);

            PollCustomizeForm pcf = new PollCustomizeForm(poll.getQuestion(), poll.getTheme(), poll.getIsPrivate(), poll.getActiveStatus(), poll.getCode());

            model.addAttribute("poll", pcf);
            model.addAttribute("pollId", poll.getId());

            return "admin/poll/adminpolledit";
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }
    }

    @PutMapping(value = "/admin/poll/{id}")
    public String editPoll(@PathVariable Long id, PollCustomizeForm pcf) {
        try {
            Poll poll = pollService.findById(id);

            switch (poll.getActiveStatus()) {
                case FINISHED:
                    return "redirect:/admin/poll/" + poll.getId();
                case OPEN:
                case CLOSED:
                    poll.setQuestion(pcf.getQuestion().replaceAll("\\s+", " ").trim());
                    poll.setTheme(pcf.getTheme().replaceAll("\\s+", " ").trim());
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
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }
    }

    @DeleteMapping(value = "/admin/poll/{id}")
    public String deletePoll(@PathVariable Long id) {
        pollService.deletePoll(id);

        return "redirect:/admin/polls";
    }
}
