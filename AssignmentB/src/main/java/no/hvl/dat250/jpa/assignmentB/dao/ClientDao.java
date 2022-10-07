package no.hvl.dat250.jpa.assignmentB.dao;

import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;

@Service
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
    public Client updateClient(String username,String firstname,String lastname,String email){
        Client client = findByUsername(username);
        if(firstname != null && !firstname.equals("")){
            client.setFirstname(firstname);
        }
        if(lastname != null && !lastname.equals("")){
            client.setLastname(lastname);
        }
        if(email != null && !email.equals("")){
            client.setEmail(email);
        }
        commit(client);
        return client;
    }

    @Override
    public Set<Poll> getPollsFromClient(String username) {
        Client client = findByUsername(username);
        return client.getOwnedPolls();
    }

    @Override
    public Client addPollToClient(String username, Poll poll) {
        Client client = findByUsername(username);
        Set<Poll> polls = client.getOwnedPolls();
        polls.add(poll);
        client.setOwnedPolls(polls);
        commit(client);
        return client;
    }

    @Override
    public Client deleteClient(String username) {
        Client client = findByUsername(username);
        em.getTransaction().begin();
        em.remove(client);
        em.getTransaction().commit();
        return client;
    }

    @Override
    public Client changeRole(String username, Role role) {
        Client client = findByUsername(username);
        client.setRole(role);
        commit(client);
        return client;
    }

    @Override
    public Role getRoleOfClient(String username) {
        return findByUsername(username).getRole();
    }
}