package no.hvl.dat250.jpa.assignmentB.api;

import no.hvl.dat250.jpa.assignmentB.dao.UserRepositoryImpl;
import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;

import java.util.Set;

public class ClientController {
    private final UserRepositoryImpl UserRepositoryImpl;

    public ClientController(UserRepositoryImpl UserRepositoryImpl) {
        this.UserRepositoryImpl = UserRepositoryImpl;
    }

    public Client findByUsername(String username){
        return UserRepositoryImpl.findByUsername(username);
    }

    public void createClient(String username, String password, Role role){
        UserRepositoryImpl.createClient(username,password,role);
    }

    public void updateClientFirstname(String username, String firstname) {
        UserRepositoryImpl.updateClientFirstname(username,firstname);
    }

    public void updateClientLastname(String username, String lastname) {
        UserRepositoryImpl.updateClientLastname(username,lastname);
    }

    public void updateClientEmail(String username, String email) {
        UserRepositoryImpl.updateClientEmail(username,email);
    }

    public Set<Poll> getPollsFromClient(String username) {
        return UserRepositoryImpl.getPollsFromClient(username);
    }

    public void addPollToClient(String username, Poll poll) {
        UserRepositoryImpl.addPollToClient(username,poll);
    }

    public void deleteClient(String username) {
        UserRepositoryImpl.deleteClient(username);
    }

    public void changeRole(String username, Role role) {
        UserRepositoryImpl.changeRole(username,role);
    }

    public Role getRoleOfClient(String username) {
        return UserRepositoryImpl.getRoleOfClient(username);
    }
}
