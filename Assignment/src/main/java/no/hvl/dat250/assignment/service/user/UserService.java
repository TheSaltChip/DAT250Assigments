package no.hvl.dat250.assignment.service.user;

import no.hvl.dat250.assignment.models.poll.Poll;
import no.hvl.dat250.assignment.models.user.Role;
import no.hvl.dat250.assignment.models.user.User;
import no.hvl.dat250.assignment.web.controller.registration.UserData;
import no.hvl.dat250.assignment.web.formobject.UserUpdateForm;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Interface for user services
 *
 * @author Sebastian
 */
public interface UserService {
    List<User> getAllUsers();

    User getUserByUsername(String username) throws NoSuchElementException;

    User saveUser(User user);

    User updateUser(String username, User user) throws NoSuchElementException;

    User updateUser(String username, UserUpdateForm user) throws NoSuchElementException;

    Set<Poll> getOwnedPollsFromUser(String username) throws NoSuchElementException;

    Poll addPollToUser(String username, Poll poll) throws NoSuchElementException;

    void deleteUser(String username);

    User changeRoleOfUser(String username, String role) throws NoSuchElementException;

    Role getRoleOfUser(String username) throws NoSuchElementException;

    User registerNewUser(UserData userData) throws Exception;
}
