package no.hvl.dat250.jpa.assignment.repository.user;

import no.hvl.dat250.jpa.assignment.models.poll.Poll;
import no.hvl.dat250.jpa.assignment.models.user.Role;
import no.hvl.dat250.jpa.assignment.models.user.User;
import no.hvl.dat250.jpa.assignment.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void InjectedDependencyAreNotNull() {
        assertThat(userService, notNullValue());
    }

    @Test
    public void CreateNewClient() {
        User user = new User("Eirik", "123", Role.Regular);
        userService.saveUser(user);

        User fetchedUser = userService.getUserByUsername("Eirik");
        assertThat(fetchedUser, is(user));
    }

    @Test
    public void UpdateClientValues() {
        User user = new User("Henrik", "123", Role.Regular);
        userService.saveUser(user);

        user.setFirstname("Henrik");
        user.setLastname("Eriksen");
        user.setEmail("henni@stud.hvl.no");

        userService.saveUser(user);

        User fetchedUser = userService.getUserByUsername("Henrik");

        assertThat(fetchedUser, notNullValue());

        assertThat(fetchedUser.getFirstname(), is("Henrik"));
        assertThat(fetchedUser.getLastname(), is("Eriksen"));
        assertThat(fetchedUser.getEmail(), is("henni@stud.hvl.no"));
    }

    @Transactional
    @Test
    public void UpdateClientPolls() {
        User user = new User("Lars", "123", Role.Regular);

        userService.saveUser(user);

        User fetchedUser = userService.getUserByUsername("Lars");

        Poll poll1 = new Poll("Poll1", "Polling", false, user);
        userService.addPollToUser("Lars", poll1);

        assertThat(fetchedUser.getOwnedPolls().size(), is(1));

        Poll poll2 = new Poll("Poll2", "Polling", false, user);
        userService.addPollToUser("Lars", poll2);

        assertThat(fetchedUser.getOwnedPolls().size(), is(2));

        Set<Poll> polls = userService.getOwnedPollsFromUser("Lars");

        assertThat(polls.size(), is(2));

    }

    @Test
    public void deleteClient() {
        User user = new User("Einar", "123", Role.Regular);

        userService.saveUser(user);

        assertThat(userService.getUserByUsername("Einar"), is(user));
        userService.deleteUser("Einar");
        assertThat(userService.getUserByUsername("Einar"), is(nullValue()));
    }

    @Test
    public void changeRoleOfClient() {
        userService.saveUser(new User("Ville", "123", Role.Regular));
        assertThat(userService.getRoleOfUser("Ville"), is(Role.Regular));

        userService.changeRoleOfUser("Ville", "Admin");
        assertThat(userService.getRoleOfUser("Ville"), is(Role.Admin));

    }
}
