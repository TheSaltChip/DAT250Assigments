package no.hvl.dat250.assignment.api;

import no.hvl.dat250.assignment.api.pojo.RoleString;
import no.hvl.dat250.assignment.persistence.models.poll.Poll;
import no.hvl.dat250.assignment.persistence.models.user.Role;
import no.hvl.dat250.assignment.persistence.models.user.User;
import no.hvl.dat250.assignment.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public List<User> findAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/user/{username}")
    public User findUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping(value = "/user")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping(value = "/user/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        return userService.updateUser(username, user);
    }

    @GetMapping(value = "/user/polls/{username}")
    public Set<Poll> getPollsFromUser(@PathVariable String username) {
        return userService.getOwnedPollsFromUser(username);
    }

    @PostMapping(value = "/user/poll/{username}")
    public Poll addPollToUser(@PathVariable String username, @RequestBody Poll poll) {
        return userService.addPollToUser(username, poll);
    }

    @DeleteMapping(value = "/user/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @PutMapping(value = "/user/role/{username}")
    public User changeRole(@PathVariable String username, @RequestBody RoleString role) {
        return userService.changeRoleOfUser(username, role.getRole());
    }

    @GetMapping(value = "/user/role/{username}")
    public Role getRoleOfClient(@PathVariable String username) {
        return userService.getRoleOfUser(username);
    }
}
