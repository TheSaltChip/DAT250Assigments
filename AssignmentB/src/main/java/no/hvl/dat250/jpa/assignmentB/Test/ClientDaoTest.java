package no.hvl.dat250.jpa.assignmentB.Test;

import no.hvl.dat250.jpa.assignmentB.dao.ClientDao;
import no.hvl.dat250.jpa.assignmentB.dao.PollDao;
import no.hvl.dat250.jpa.assignmentB.driver.Main;
import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClientDaoTest {

    private EntityManagerFactory factory;
    private EntityManager em;
    private ClientDao clientDao;

    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_NAME);
        em = factory.createEntityManager();
        clientDao = new ClientDao();
    }

    @Test
    public void CreateNewClient() {
        clientDao.createClient("Eirik", "123", Role.Regular);
        Client client = clientDao.findByUsername("Eirik");
        assertThat(client.getUsername(), is("Eirik"));
    }

    @Test
    public void UpdateClientValues() {
        clientDao.createClient("Henrik", "123", Role.Regular);
        clientDao.updateClientFirstname("Henrik", "Henrik");
        clientDao.updateClientLastname("Henrik", "Eriksen");
        clientDao.updateClientEmail("Henrik", "henni@stud.hvl.no");

        Client client = clientDao.findByUsername("Henrik");
        assertThat(client.getFirstname(), is("Henrik"));
        assertThat(client.getLastname(), is("Eriksen"));
        assertThat(client.getEmail(), is("henni@stud.hvl.no"));
    }

    @Test
    public void UpdateClientPolls() {
        clientDao.createClient("Lars", "123", Role.Regular);
        Client client = clientDao.findByUsername("Lars");

        Poll poll1 = new Poll("Poll1", "Polling", false, LocalDateTime.now(), client);
        clientDao.addPollToClient("Lars", poll1);

        assertThat(client.getOwnedPolls().size(), is(1));

        Poll poll2 = new Poll("Poll2", "Polling", false, LocalDateTime.now(), client);
        clientDao.addPollToClient("Lars", poll2);
        assertThat(client.getOwnedPolls().size(), is(2));

        assertThat(clientDao.getPollsFromClient("Lars").size(), is(2));
    }

    @Test
    public void deleteClient() {
        clientDao.createClient("Einar", "123", Role.Regular);

        assertThat(clientDao.findByUsername("Einar"), is(Client.class));
        clientDao.deleteClient("Einar");
        assertThat(em.find(Client.class, "Einar"), is(nullValue()));
    }

    @Test
    public void changeRoleOfClient() {
        clientDao.createClient("Ville", "123", Role.Regular);
        assertThat(clientDao.getRoleOfClient("Ville"), is(Role.Regular));

        clientDao.changeRole("Ville", Role.Admin);
        assertThat(clientDao.getRoleOfClient("Ville"), is(Role.Admin));

    }
}
