package no.hvl.dat250.jpa.assignmentB.clientdao;

import no.hvl.dat250.jpa.assignmentB.dao.UserRepository;
import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;


@DataJpaTest
public class CustomUserImplTest {
    @Autowired
    private UserRepository userRepository;


    @Test
    public void CreateNewClient() {
        userRepository.createClient("Eirik", "123", Role.Regular);
        Client client = userRepository.findByUsername("Eirik");
        assertThat(client.getUsername(), is("Eirik"));
    }

    @Test
    public void UpdateClientValues() {
        userRepository.createClient("Henrik", "123", Role.Regular);
        userRepository.updateClientFirstname("Henrik", "Henrik");
        userRepository.updateClientLastname("Henrik", "Eriksen");
        userRepository.updateClientEmail("Henrik", "henni@stud.hvl.no");

        Client client = userRepository.findByUsername("Henrik");
        assertThat(client.getFirstname(), is("Henrik"));
        assertThat(client.getLastname(), is("Eriksen"));
        assertThat(client.getEmail(), is("henni@stud.hvl.no"));
    }

    @Test
    public void UpdateClientPolls() {
        userRepository.createClient("Lars", "123", Role.Regular);
        Client client = userRepository.findByUsername("Lars");

        Poll poll1 = new Poll("Poll1", "Polling", false, LocalDateTime.now(), client);
        userRepository.addPollToClient("Lars", poll1);

        assertThat(client.getOwnedPolls().size(), is(1));

        Poll poll2 = new Poll("Poll2", "Polling", false, LocalDateTime.now(), client);
        userRepository.addPollToClient("Lars", poll2);
        assertThat(client.getOwnedPolls().size(), is(2));

        assertThat(userRepository.getPollsFromClient("Lars").size(), is(2));
    }

    @Test
    public void deleteClient() {
        Client client = userRepository.createClient("Einar", "123", Role.Regular);

        assertThat(userRepository.findByUsername("Einar"), is(client));
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
