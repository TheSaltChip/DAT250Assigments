package no.hvl.dat250.assignment.service.user;

import lombok.NonNull;
import no.hvl.dat250.assignment.persistence.models.poll.Poll;
import no.hvl.dat250.assignment.persistence.models.user.Role;
import no.hvl.dat250.assignment.persistence.models.user.User;
import no.hvl.dat250.assignment.persistence.repository.user.UserRepository;
import no.hvl.dat250.assignment.web.controller.registration.UserData;
import no.hvl.dat250.assignment.web.formObject.UserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) throws NoSuchElementException {
        return userRepository.findById(username).orElseThrow();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        if (user.getRole().equals(Role.Admin))
            user.setRole(Role.Regular);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(String username, User user) throws NoSuchElementException {
        User updatedUser = userRepository.findById(username).orElseThrow();

        updatedUser.setFirstname(user.getFirstname().replaceAll("\\s+", " ").trim());
        updatedUser.setLastname(user.getLastname().replaceAll("\\s+", " ").trim());
        updatedUser.setEmail(user.getEmail().replaceAll("\\s+", " ").trim());
        updatedUser.setPassword(user.getPassword().replaceAll("\\s+", " ").trim());

        return userRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public User updateUser(String username, UserUpdateForm user) throws NoSuchElementException {
        User updatedUser = userRepository.findById(username).orElseThrow();

        updatedUser.setFirstname(user.getFirstname().replaceAll("\\s+", " ").trim());
        updatedUser.setLastname(user.getLastname().replaceAll("\\s+", " ").trim());
        updatedUser.setEmail(user.getEmail().replaceAll("\\s+", " ").trim());

        return userRepository.save(updatedUser);
    }

    @Override
    public Set<Poll> getOwnedPollsFromUser(String username) throws NoSuchElementException {
        return userRepository.findById(username).orElseThrow().getOwnedPolls();
    }

    @Override
    @Transactional
    public Poll addPollToUser(String username, Poll poll) throws NoSuchElementException {
        User c = userRepository.findById(username).orElseThrow();

        c.getOwnedPolls().add(poll);
        userRepository.save(c);

        return poll;
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    @Override
    @Transactional
    public User changeRoleOfUser(String username, String role) throws NoSuchElementException {
        Role roleObj = Role.valueOf(role);
        User user = userRepository.findById(username).orElseThrow();

        user.setRole(roleObj);

        return userRepository.save(user);
    }

    @Override
    public Role getRoleOfUser(String username) throws NoSuchElementException {
        User user = userRepository.findById(username).orElseThrow();

        return user.getRole();
    }

    @Override
    @Transactional
    public User registerNewUser(@NonNull UserData userData) throws Exception {
        if (existingUserCheck(userData.getUsername())) {
            throw new Exception("Account with this username already exists");
        }

        User user = userData.createUser();

        return userRepository.save(user);
    }

    public boolean existingUserCheck(String username) {
        return userRepository.existsUsersByUsername(username);
    }
}
