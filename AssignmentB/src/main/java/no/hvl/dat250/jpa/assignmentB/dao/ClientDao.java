package no.hvl.dat250.jpa.assignmentB.dao;

import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;

public class ClientDao implements IClientDao {
    public static final String PERSISTENCE_UNIT_NAME = "assignmentB";
    EntityManagerFactory factory;
    EntityManager em;

    public ClientDao() {
        this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.em = factory.createEntityManager();
    }

    public void commit(Client client){
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
    }

    @Override
    public Client findByUsername(String username) {
        Client client = em.find(Client.class, username);
        return client;
    }

    @Override
    public void createClient(String username, String password, Role role) {
        Client client = new Client(username, password, role);
        commit(client);
    }

    @Override
    public void updateClientFirstname(String username, String firstname) {
        Client client = findByUsername(username);
        client.setFirstname(firstname);
        commit(client);
    }

    @Override
    public void updateClientLastname(String username, String lastname) {
        Client client = findByUsername(username);
        client.setLastname(lastname);
        commit(client);
    }

    @Override
    public void updateClientEmail(String username, String email) {
        Client client = findByUsername(username);
        client.setEmail(email);
        commit(client);
    }

    @Override
    public Set<Poll> getPollsFromClient(String username) {
        Client client = findByUsername(username);
        return client.getOwnedPolls();
    }

    @Override
    public void addPollToClient(String username, Poll poll) {
        Client client = findByUsername(username);
        Set<Poll> polls = client.getOwnedPolls();
        polls.add(poll);
        client.setOwnedPolls(polls);
        commit(client);
    }

    @Override
    public void deleteClient(String username) {
        em.getTransaction().begin();
        em.remove(findByUsername(username));
        em.getTransaction().commit();
    }

    @Override
    public void changeRole(String username, Role role) {
        Client client = findByUsername(username);
        client.setRole(role);
        commit(client);
    }

    @Override
    public Role getRoleOfClient(String username) {
        return findByUsername(username).getRole();
    }
}