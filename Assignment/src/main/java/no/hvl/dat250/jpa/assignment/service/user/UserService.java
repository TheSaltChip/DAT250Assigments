package no.hvl.dat250.jpa.assignment.service.user;

import no.hvl.dat250.jpa.assignment.web.controller.registration.UserData;
import no.hvl.dat250.jpa.assignment.models.poll.Poll;
import no.hvl.dat250.jpa.assignment.models.user.Role;
import no.hvl.dat250.jpa.assignment.models.user.User;
import no.hvl.dat250.jpa.assignment.web.formobject.UserUpdateForm;

import java.util.List;
import java.util.Set;

/**
 * Interface for user services
 *
 * @author Sebastian
 */
public interface UserService {
    List<User> getAllUsers();

    User getUserByUsername(String username);

    User saveUser(User user);

    User updateUser(String username, User user);

    User updateUser(String username, UserUpdateForm user);

    Set<Poll> getOwnedPollsFromUser(String username);

    Poll addPollToUser(String username, Poll poll);

    void deleteUser(String username);

    User changeRoleOfUser(String username, String role);

    Role getRoleOfUser(String username);

    User registerNewUser(UserData userData) throws Exception;
}
