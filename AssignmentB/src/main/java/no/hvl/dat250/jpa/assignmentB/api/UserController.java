package no.hvl.dat250.jpa.assignmentB.api;

import no.hvl.dat250.jpa.assignmentB.models.User;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;
import no.hvl.dat250.jpa.assignmentB.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user")
    public User user() {
        return new User("s", "s", Role.Regular);
    }

    @GetMapping(value = "/user/{username}")
    public User findUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/user/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        return userService.updateUser(username, user);
    }

    @GetMapping(value = "/polls/{username}")
    public Set<Poll> getPollsFromUser(@PathVariable String username) {
        return userService.getOwnedPollsFromUser(username);
    }

    @PostMapping(path = "user/poll/{username}")
    public Poll addPollToUser(@PathVariable String username, @RequestBody Poll poll) {
        return userService.addPollToUser(username, poll);
    }

    @DeleteMapping("user/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

    /* Isn't this covered in update user?
    @PutMapping("/role/{username}")
    public User changeRole(@PathVariable String username, @RequestBody Role role) {
        return userService.changeRole(username, role);
    }*/

    @GetMapping("user/role/{username}")
    public Role getRoleOfClient(@PathVariable String username) {
        return userService.getRoleOfUser(username);
    }
}
