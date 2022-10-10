package no.hvl.dat250.jpa.assignment.repository.poll;

import no.hvl.dat250.jpa.assignment.models.User;
import no.hvl.dat250.jpa.assignment.models.Poll;

import java.time.LocalDateTime;

/**
 * Interface for Poll DAOs
 *
 * @author Sebastian
 */
public interface PollRepositoryCustom {
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
    User getOwner(int pollId);

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
     * @param startDate New start date
     * @param endDate   New end date
     */
    void updateTime(int pollId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Creates a new poll
     *
     * @param name        Name of the poll
     * @param theme       Theme of the poll
     * @param isPrivate   True if private, false if public
     * @param active      True if active, false if not active
     * @param createdDate Date and time the poll was created
     * @param user        Which client created the poll
     */
    void createPoll(String name, String theme, boolean isPrivate, boolean active, LocalDateTime createdDate, User user);

    /**
     * Creates a new time limit poll
     *
     * @param name        Name of the poll
     * @param theme       Theme of the poll
     * @param isPrivate   True if private, false if public
     * @param active      True id the poll is active, false if not
     * @param createdDate Date and time the poll was created
     * @param user        Which client created the poll
     * @param startDate   The date at which the poll starts
     * @param endDate     The date at which the poll ends
     */
    void createTimeLimitPoll(String name, String theme, boolean isPrivate, boolean active, LocalDateTime createdDate, User user,
                             LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Deletes the poll with the given pollId
     *
     * @param pollId ID of the poll
     */
    void deletePoll(int pollId);
}
