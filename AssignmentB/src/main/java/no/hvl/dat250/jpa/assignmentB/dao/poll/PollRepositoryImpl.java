package no.hvl.dat250.jpa.assignmentB.dao.poll;

import no.hvl.dat250.jpa.assignmentB.models.User;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.TimeLimitPoll;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

@Service
public class PollRepositoryImpl implements PollRepositoryCustom {
    public static final String PERSISTENCE_UNIT_NAME = "assignmentB";
    EntityManagerFactory factory;
    EntityManager em;

    public void setUp() {
        this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.em = factory.createEntityManager();
    }

    public void commit(Poll poll) {
        em.persist(poll);
        em.getTransaction().commit();
        em.close();
    }


    public Poll findById(int pollId) {
        setUp();
        em.getTransaction().begin();
        Poll poll = em.find(Poll.class, pollId);
        em.close();
        return poll;
    }

    @Override
    public User getOwner(int pollId) {
        setUp();
        em.getTransaction().begin();
        Poll poll = em.find(Poll.class, pollId);
        em.close();
        User owner = poll.getOwner();
        return owner;
    }

    @Override
    public void updateVote(boolean yesOrNo, int pollId) {

    }

    @Override
    public void updateVote(int yes, int no, int pollId) {

    }
/*
    public void updateVote(boolean yesOrNo, int pollId){
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        if(yesOrNo){
            poll.setYesVotes(poll.getYesVotes() + 1);
        }else{
            poll.setNoVotes(poll.getNoVotes() + 1);
        }

        commit(poll);
    }

    @Override
    public void updateVote(int yes, int no, int pollId) {
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        int currentYes = poll.getYesVotes();
        int currentNo = poll.getNoVotes();
        poll.setYesVotes(currentYes+yes);
        poll.setNoVotes(currentNo+no);
        commit(poll);
    }*/

    @Override
    public void updatePoll(int pollId, String name, String theme) {
        setUp();
        Poll poll = em.find(Poll.class, pollId);
        if (name != null && !name.equals("")) {
            poll.setName(name);
        }
        if (theme != null && !theme.equals("")) {
            poll.setTheme(theme);
        }
        commit(poll);
    }

    @Override
    public void closePoll(int pollId) {
        setUp();
        Poll poll = em.find(Poll.class, pollId);
        poll.setActive(false);
        commit(poll);
    }

    @Override
    public void openPoll(int pollId) {
        setUp();
        Poll poll = em.find(Poll.class, pollId);
        poll.setActive(true);
        commit(poll);
    }

    @Override
    public void updateTime(int pollId, LocalDateTime startDate, LocalDateTime endDate) {
        setUp();
        TimeLimitPoll poll = em.find(TimeLimitPoll.class, pollId);
        if (poll != null) {
            poll.setStartDate(startDate);
            poll.setEndDate(endDate);
            commit(poll);
        }
    }

    @Override
    public void createPoll(String name, String theme, boolean isPrivate, boolean active, LocalDateTime createdDate, User user) {
        setUp();
        Poll poll = new Poll(name, theme, isPrivate, active, createdDate, user);
        commit(poll);
    }

    @Override
    public void createTimeLimitPoll(String name, String theme, boolean isPrivate, boolean active, LocalDateTime createdDate, User user, LocalDateTime startDate, LocalDateTime endDate) {
        setUp();
        TimeLimitPoll poll = new TimeLimitPoll(name, theme, isPrivate, active, createdDate, user, startDate, endDate);
        commit(poll);
    }

    @Override
    public void deletePoll(int pollId) {
        setUp();
        em.remove(em.find(Poll.class, pollId));
        em.getTransaction().commit();
        em.close();
    }

}
