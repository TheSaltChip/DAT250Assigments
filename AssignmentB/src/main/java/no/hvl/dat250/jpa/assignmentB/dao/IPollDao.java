package no.hvl.dat250.jpa.assignmentB.dao;

import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.TimeLimitPoll;

import java.sql.Time;
import java.time.LocalDateTime;

/**
 * Interface for Poll DAOs
 *
 * @author Sebastian
 */
public interface IPollDao {
    /**
     * Attempts to find a poll with the given id
     *
     * @param pollId The poll id
     * @return A poll with pollId as ID, null if not
     */
    Poll findById(int pollId);

    /**
     * Gets the owner of the poll with the given pollId
     *
     * @param pollId The poll id
     * @return Client object which owns the poll
     */
    Client getOwner(int pollId);

    /**
     * Updates the votes of the poll with the given pollId
     * if the poll exists, based on the value of the yesOrNo boolean
     * If yesOrNo is true increment yes, if false increment no
     *
     * @param yesOrNo Boolean variable indicates which vote to increment
     * @param pollId  ID of the poll to update the poll
     */
    Poll updateVote(boolean yesOrNo, int pollId);

    /**
     * Adds the votes of the poll with the given values for yes and no
     *
     * @param yes    Amount of yes votes to be added
     * @param no     Amount of no votes to be added
     * @param pollId Poll id
     */
    Poll updateVote(int yes, int no, int pollId);

    /**
     * Updates the name or theme of the poll with the given pollId
     *
     * @param pollId ID of the poll
     * @param name   New name
     * @param theme  New theme
     */
    Poll updatePoll(int pollId, String name, String theme);

    /**
     * Closes the poll with the given id
     *
     * @param pollId ID of the poll
     */
    Poll closePoll(int pollId);

    /**
     * Opens the poll with the given pollId
     *
     * @param pollId ID of the poll
     */
    Poll openPoll(int pollId);

    /**
     * Updates the start or end date for the poll with the given pollId,
     * if the class is of type TimeLimitPoll
     *
     * @param pollId    ID of the poll
     * @param startDate New start date
     * @param endDate   New end date
     */
    TimeLimitPoll updateTime(int pollId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Creates a new poll
     *
     * @param name        Name of the poll
     * @param theme       Theme of the poll
     * @param isPrivate   True if private, false if public
     * @param createdDate Date and time the poll was created
     * @param client      Which client created the poll
     */
    Poll createPoll(String name, String theme, boolean isPrivate, LocalDateTime createdDate, Client client);

    /**
     * Creates a new time limit poll
     *
     * @param name        Name of the poll
     * @param theme       Theme of the poll
     * @param isPrivate   True if private, false if public
     * @param createdDate Date and time the poll was created
     * @param client      Which client created the poll
     */
    TimeLimitPoll createTimeLimitPoll(String name, String theme, boolean isPrivate, LocalDateTime createdDate, Client client,
                             LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Deletes the poll with the given pollId
     *
     * @param pollId ID of the poll
     */
    Poll deletePoll(int pollId);
}
