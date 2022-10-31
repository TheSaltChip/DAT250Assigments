package no.hvl.dat250.jpa.assignment.service.user;

import lombok.NonNull;
import no.hvl.dat250.jpa.assignment.controller.Registration.UserData;
import no.hvl.dat250.jpa.assignment.repository.user.UserRepository;
import no.hvl.dat250.jpa.assignment.models.Poll;
import no.hvl.dat250.jpa.assignment.models.Role;
import no.hvl.dat250.jpa.assignment.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return userRepository.findById(username).orElseThrow();
    }

    @Override
    public User saveUser(User user) {
        if (user.getRole().equals(Role.Admin))
            user.setRole(Role.Regular);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(String username, User user) {
        User updatedUser = userRepository.findById(username).orElseThrow();

        updatedUser.setFirstname(user.getFirstname());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setRole(user.getRole());

        return userRepository.save(updatedUser);
    }

    @Override
    public Set<Poll> getOwnedPollsFromUser(String username) {
        User c = userRepository.findById(username).orElseThrow();
        return c.getOwnedPolls();
    }

    @Override
    public Poll addPollToUser(String username, Poll poll) {
        User c = userRepository.findById(username).orElseThrow();

        c.getOwnedPolls().add(poll);
        userRepository.save(c);

        return poll;
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    @Override
    public User changeRoleOfUser(String username, String role) {
        Role roleObj = Role.valueOf(role);
        User user = userRepository.findById(username).orElseThrow();

        user.setRole(roleObj);

        return userRepository.save(user);
    }

    @Override
    public Role getRoleOfUser(String username) {
        User user = userRepository.findById(username).orElseThrow();

        return user.getRole();
    }

    public User registerNewUser(@NonNull UserData userData) throws Exception {

        if(existingUserCheck(userData.getUsername())) {
            throw new Exception("Account with this username already exists");
        }

        User user = new User();

        user.setFirstname(userData.getFirstName());
        user.setLastname(userData.getLastName());
        user.setUsername(userData.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userData.getPassword()));
        user.setEmail(userData.getEmail());
        user.setRole(Role.Regular);

        return saveUser(user);
    }

    public boolean existingUserCheck(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }
}
