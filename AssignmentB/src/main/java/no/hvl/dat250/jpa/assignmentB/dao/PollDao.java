package no.hvl.dat250.jpa.assignmentB.dao;

import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.TimeLimitPoll;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import java.time.LocalDateTime;

@Service
public class PollDao implements IPollDao {
    public static final String PERSISTENCE_UNIT_NAME = "assignmentB";
    EntityManagerFactory factory;
    EntityManager em;

    public void setUp(){
        this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.em = factory.createEntityManager();
    }

    public void commit(Poll poll){
        em.persist(poll);
        em.getTransaction().commit();
        em.close();
    }


    public Poll findById(int pollId){
        setUp();
        em.getTransaction().begin();
        Poll poll = em.find(Poll.class,pollId);
        em.close();
        return poll;
    }

    @Override
    public Client getOwner(int pollId) {
        setUp();
        em.getTransaction().begin();
        Poll poll = em.find(Poll.class,pollId);
        em.close();
        Client owner = poll.getOwner();
        return owner;
    }

    public Poll updateVote(boolean yesOrNo, int pollId){
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        if(yesOrNo){
            poll.setYesVotes(poll.getYesVotes() + 1);
        }else{
            poll.setNoVotes(poll.getNoVotes() + 1);
        }
        commit(poll);
        return poll;
    }

    @Override
    public Poll updateVote(int yes, int no, int pollId) {
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        int currentYes = poll.getYesVotes();
        int currentNo = poll.getNoVotes();
        poll.setYesVotes(currentYes+yes);
        poll.setNoVotes(currentNo+no);
        commit(poll);
        return poll;
    }

    @Override
    public Poll updatePoll(int pollId, String name, String theme) {
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        if(name != null && !name.equals("")){
            poll.setName(name);
        }
        if(theme != null && !theme.equals("")){
            poll.setTheme(theme);
        }
        commit(poll);
        return poll;
    }

    @Override
    public Poll closePoll(int pollId) {
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        poll.setActive(false);
        commit(poll);
        return poll;
    }

    @Override
    public Poll openPoll(int pollId) {
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        poll.setActive(true);
        commit(poll);
        return poll;
    }

    @Override
    public TimeLimitPoll updateTime(int pollId, LocalDateTime startDate, LocalDateTime endDate) {
        setUp();
        TimeLimitPoll poll = em.find(TimeLimitPoll.class,pollId);
        if(poll != null) {
            poll.setStartDate(startDate);
            poll.setEndDate(endDate);
            commit(poll);
        }
        return poll;
    }

    @Override
    public Poll createPoll(String name, String theme, boolean isPrivate, LocalDateTime createdDate, Client client) {
        setUp();
        Poll poll = new Poll(name,theme,isPrivate,createdDate,client);
        commit(poll);
        return poll;
    }

    @Override
    public TimeLimitPoll createTimeLimitPoll(String name, String theme, boolean isPrivate, LocalDateTime createdDate, Client client, LocalDateTime startDate, LocalDateTime endDate) {
        setUp();
        TimeLimitPoll poll = new TimeLimitPoll(name,theme,isPrivate,createdDate,client,startDate,endDate);
        commit(poll);
        return poll;
    }

    @Override
    public Poll deletePoll(int pollId) {
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        em.remove(poll);
        em.getTransaction().commit();
        em.close();
        return poll;
    }

}
