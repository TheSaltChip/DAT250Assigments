package no.hvl.dat250.jpa.assignmentB.dao.client;

import no.hvl.dat250.jpa.assignmentB.models.User;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void InjectedDependencyAreNotNull() {
        assertThat(userRepository, notNullValue());
    }

    @Test
    public void CreateNewClient() {
        userRepository.createClient("Eirik", "123", Role.Regular);
        User user = userRepository.findByUsername("Eirik");
        assertThat(user.getUsername(), is("Eirik"));
    }

    @Test
    public void UpdateClientValues() {
        userRepository.createClient("Henrik", "123", Role.Regular);
        userRepository.updateClientFirstname("Henrik", "Henrik");
        userRepository.updateClientLastname("Henrik", "Eriksen");
        userRepository.updateClientEmail("Henrik", "henni@stud.hvl.no");

        User user = userRepository.findByUsername("Henrik");
        assertThat(user.getFirstname(), is("Henrik"));
        assertThat(user.getLastname(), is("Eriksen"));
        assertThat(user.getEmail(), is("henni@stud.hvl.no"));
    }

    @Test
    public void UpdateClientPolls() {
        userRepository.createClient("Lars", "123", Role.Regular);
        User user = userRepository.findByUsername("Lars");

        Poll poll1 = new Poll("Poll1", "Polling", false, LocalDateTime.now(), user);
        userRepository.addPollToClient("Lars", poll1);

        assertThat(user.getOwnedPolls().size(), is(1));

        Poll poll2 = new Poll("Poll2", "Polling", false, LocalDateTime.now(), user);
        userRepository.addPollToClient("Lars", poll2);
        assertThat(user.getOwnedPolls().size(), is(2));

        assertThat(userRepository.getPollsFromClient("Lars").size(), is(2));
    }

    @Test
    public void deleteClient() {
        User user = userRepository.createClient("Einar", "123", Role.Regular);

        assertThat(userRepository.findByUsername("Einar"), is(user));
        userRepository.deleteClient("Einar");
        assertThat(userRepository.findByUsername("Einar"), is(nullValue()));
    }

    @Test
    public void changeRoleOfClient() {
        userRepository.createClient("Ville", "123", Role.Regular);
        assertThat(userRepository.getRoleOfClient("Ville"), is(Role.Regular));

        userRepository.changeRole("Ville", Role.Admin);
        assertThat(userRepository.getRoleOfClient("Ville"), is(Role.Admin));

    }
}
