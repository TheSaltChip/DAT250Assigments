package no.hvl.dat250.jpa.assignmentB.api;

import no.hvl.dat250.jpa.assignmentB.dao.ClientDao;
import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;

import java.util.Set;

public class ClientController {
    private final ClientDao clientDao;

    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Client findByUsername(String username){
        return clientDao.findByUsername(username);
    }

    public void createClient(String username, String password, Role role){
        clientDao.createClient(username,password,role);
    }

    public void updateClientFirstname(String username, String firstname) {
        clientDao.updateClientFirstname(username,firstname);
    }

    public void updateClientLastname(String username, String lastname) {
        clientDao.updateClientLastname(username,lastname);
    }

    public void updateClientEmail(String username, String email) {
        clientDao.updateClientEmail(username,email);
    }

    public Set<Poll> getPollsFromClient(String username) {
        return clientDao.getPollsFromClient(username);
    }

    public void addPollToClient(String username, Poll poll) {
        clientDao.addPollToClient(username,poll);
    }

    public void deleteClient(String username) {
        clientDao.deleteClient(username);
    }

    public void changeRole(String username, Role role) {
        clientDao.changeRole(username,role);
    }

    public Role getRoleOfClient(String username) {
        return clientDao.getRoleOfClient(username);
    }
}
