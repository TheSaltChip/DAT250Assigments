package no.hvl.dat250.jpa.assignmentB.dao;

import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;

public class ClientDao implements IClientDao {
    public static final String PERSISTENCE_UNIT_NAME = "assignmentB";
    EntityManagerFactory factory;
    EntityManager em;

    public void setUp() {
        this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.em = factory.createEntityManager();
        em.getTransaction().begin();
    }

    public void commit(Client client){
        em.persist(client);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Client findByUsername(String username) {
        setUp();
        em.getTransaction().begin();
        Client client = em.find(Client.class, username);
        em.close();
        return client;
    }

    @Override
    public void createClient(String username, String password) {
        setUp();
        Client client = new Client(username, password);
        commit(client);
    }

    @Override
    public void updateClientFirstname(String username, String firstname) {

    }

    @Override
    public void updateClientLastname(String username, String lastname) {

    }

    @Override
    public void updateClientEmail(String username, String email) {

    }

    @Override
    public Set<Poll> getPollsFromClient(String username) {
        return null;
    }

    @Override
    public void addPollToClient(String username, Poll poll) {

    }

    @Override
    public void deleteClient(String username) {

    }
}