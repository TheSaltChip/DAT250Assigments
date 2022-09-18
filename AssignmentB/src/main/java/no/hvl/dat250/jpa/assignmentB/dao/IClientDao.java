package no.hvl.dat250.jpa.assignmentB.dao;

import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;

import java.util.Set;

/**
 * Interface for client DAOs
 *
 * @author Sebastian
 */
public interface IClientDao {
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
     */
    void createClient(String username, String password);

    /**
     * Updates the firstname of the client
     *
     * @param username  Username of the client
     * @param firstname The new firstname
     */
    void updateClientFirstname(String username, String firstname);

    /**
     * Updates the lastname of the client
     *
     * @param username Username of the client
     * @param lastname The new lastname
     */
    void updateClientLastname(String username, String lastname);

    /**
     * Updates the email of the client
     *
     * @param username Username of the client
     * @param email    The new email
     */
    void updateClientEmail(String username, String email);

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
    void addPollToClient(String username, Poll poll);

    /**
     * Deletes the client with the given username
     *
     * @param username The username of the client
     */
    void deleteClient(String username);

    /**
     * Changes the role of the client with the given username
     *
     * @param username The username of the client
     * @param role     The new role
     */
    void changeRole(String username, Role role);

    /**
     * Returns the role of the client with the given username
     *
     * @param username The username of the client
     * @return The role of the client
     */
    Role getRoleOfClient(String username);
}

