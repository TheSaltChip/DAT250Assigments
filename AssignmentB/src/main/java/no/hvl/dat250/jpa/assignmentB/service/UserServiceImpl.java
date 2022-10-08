package no.hvl.dat250.jpa.assignmentB.service;

import no.hvl.dat250.jpa.assignmentB.dao.client.UserRepository;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;
import no.hvl.dat250.jpa.assignmentB.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
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
    public User getUserByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String username, User user) {
        User updatedUser = userRepository.findById(username).orElse(null);
        if (updatedUser == null) {
            return null;
        }

        updatedUser.setFirstname(user.getFirstname());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setRole(user.getRole());

        return userRepository.save(updatedUser);
    }

    @Override
    public Set<Poll> getOwnedPollsFromUser(String username) {
        User c = userRepository.findById(username).orElse(null);
        if (c == null) return null;
        return c.getOwnedPolls();
    }

    @Override
    public Poll addPollToUser(String username, Poll poll) {
        User c = userRepository.findById(username).orElse(null);

        if (c == null) return null;

        c.getOwnedPolls().add(poll);
        userRepository.save(c);

        return poll;
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    @Override
    public User changeRoleOfUser(String username, Role role) {
        User user = userRepository.findById(username).orElse(null);

        if (user == null) return null;

        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public Role getRoleOfUser(String username) {
        User user = userRepository.findById(username).orElse(null);

        return user == null ? null : user.getRole();
    }
}
