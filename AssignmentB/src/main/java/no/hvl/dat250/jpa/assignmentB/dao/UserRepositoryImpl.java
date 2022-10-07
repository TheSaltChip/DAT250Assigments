package no.hvl.dat250.jpa.assignmentB.dao;

import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

public class UserRepositoryImpl implements UserRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Override
    public Client findByUsername(String username) {
        return em.find(Client.class, username);
    }

    @Override
    public Client createClient(String username, String password, Role role) {
        Client client = new Client(username, password, role);
        em.persist(client);
        return client;
    }

    @Override
    public void updateClientFirstname(String username, String firstname) {
        Client client = findByUsername(username);
        client.setFirstname(firstname);
        em.merge(client);
    }

    @Override
    public void updateClientLastname(String username, String lastname) {
        Client client = findByUsername(username);
        client.setLastname(lastname);
        em.merge(client);
    }

    @Override
    public void updateClientEmail(String username, String email) {
        Client client = findByUsername(username);
        client.setEmail(email);
        em.merge(client);
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
        em.merge(client);
    }

    @Override
    public void deleteClient(String username) {
        em.remove(findByUsername(username));
    }

    @Override
    public void changeRole(String username, Role role) {
        Client client = findByUsername(username);
        client.setRole(role);
        em.merge(client);
    }

    @Override
    public Role getRoleOfClient(String username) {
        return findByUsername(username).getRole();
    }
}