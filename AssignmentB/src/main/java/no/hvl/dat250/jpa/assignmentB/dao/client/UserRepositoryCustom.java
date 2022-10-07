package no.hvl.dat250.jpa.assignmentB.dao.client;

import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;

import java.util.Set;

/**
 * Interface for client DAOs
 *
 * @author Sebastian
 */
public interface UserRepositoryCustom {
    /**
     * Attempts to find a client by the given username
     *
     * @param username Username of the client
     * @return A client object if there exists a client with the username, null if not
     */
    Client findByUsername(String username);

    /**
     * Creates a client with the given username and password
     *
     * @param username Username of the client
     * @param password Password of the client, which we should salt and hash
     * @param role Role of the client
     * @return The created client
     */
    Client createClient(String username, String password, Role role);

    /**
     * updates the firstname, lastname and email of a client
     * @param username
     * @param firstname
     * @param lastname
     * @param email
     */
    Client updateClient(String username,String firstname,String lastname,String email);

     /**
     * Gets all polls from the client with the username
     *
     * @param username Username of the client
     * @return Set of polls
     */
    Set<Poll> getPollsFromClient(String username);

    /**
     * Adds a poll to the client with the given username
     *
     * @param username The username of the client
     * @param poll     The new poll
     */
    Client addPollToClient(String username, Poll poll);

    /**
     * Deletes the client with the given username
     *
     * @param username The username of the client
     */
    Client deleteClient(String username);

    /**
     * Changes the role of the client with the given username
     *
     * @param username The username of the client
     * @param role     The new role
     */
    Client changeRole(String username, Role role);

    /**
     * Returns the role of the client with the given username
     *
     * @param username The username of the client
     * @return The role of the client
     */
    Role getRoleOfClient(String username);
}

