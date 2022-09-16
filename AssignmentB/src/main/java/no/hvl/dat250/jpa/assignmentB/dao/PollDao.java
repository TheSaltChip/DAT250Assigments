package no.hvl.dat250.jpa.assignmentB.dao;

import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;

import java.time.LocalDateTime;

/**
 * Interface for Poll DAOs
 *
 * @author Sebastian
 */
public interface PollDao {
    /**
     * Attempts to find a poll with the given id
     *
     * @param pollId The poll id
     * @return A poll with pollId as ID, null if not
     */
    Poll findById(int pollId);

    /**
     * Updates the votes of the poll with the given pollId
     * if the poll exists, based on the value of the yesOrNo boolean
     * If yesOrNo is true increment yes, if false increment no
     *
     * @param yesOrNo Boolean variable indicates which vote to increment
     * @param pollId  ID of the poll to update the poll
     */
    void updateVote(boolean yesOrNo, int pollId);

    /**
     * Adds the votes of the poll with the given values for yes and no
     *
     * @param yes    Amount of yes votes to be added
     * @param no     Amount of no votes to be added
     * @param pollId Poll id
     */
    void updateVote(int yes, int no, int pollId);

    /**
     * Updates the name or theme of the poll with the given pollId
     *
     * @param pollId ID of the poll
     * @param name   New name
     * @param theme  New theme
     */
    void updatePoll(int pollId, String name, String theme);

    /**
     * Closes the poll with the given id
     *
     * @param pollId ID of the poll
     */
    void closePoll(int pollId);

    /**
     * Opens the poll with the given pollId
     *
     * @param pollId ID of the poll
     */
    void openPoll(int pollId);

    /**
     * Updates the start or end date for the poll with the given pollId,
     * if the class is of type TimeLimitPoll
     *
     * @param pollId    ID of the poll
     * @param c         Class of the desired poll
     * @param startDate New start date
     * @param endDate   New end date
     */
    <T> void updateTime(int pollId, Class<T> c, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Creates a new poll
     *
     * @param name        Name of the poll
     * @param theme       Theme of the poll
     * @param isPrivate   True if private, false if public
     * @param createdDate Date and time the poll was created
     * @param client      Which client created the poll
     */
    void createPoll(String name, String theme, boolean isPrivate, LocalDateTime createdDate, Client client);

    /**
     * Creates a new time limit poll
     *
     * @param name        Name of the poll
     * @param theme       Theme of the poll
     * @param isPrivate   True if private, false if public
     * @param createdDate Date and time the poll was created
     * @param client      Which client created the poll
     */
    void createTimeLimitPoll(String name, String theme, boolean isPrivate, LocalDateTime createdDate, Client client,
                             LocalDateTime startDate, LocalDateTime endDate);


    /**
     * Deletes the poll with the given pollId
     *
     * @param pollId ID of the poll
     */
    void deletePoll(int pollId);


}
