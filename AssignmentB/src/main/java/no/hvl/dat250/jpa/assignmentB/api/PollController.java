package no.hvl.dat250.jpa.assignmentB.api;

import no.hvl.dat250.jpa.assignmentB.dao.ClientDao;
import no.hvl.dat250.jpa.assignmentB.dao.PollDao;
import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.TimeLimitPoll;

import java.time.LocalDateTime;

public class PollController {

    private final PollDao pollDao;

    public PollController(PollDao pollDao) {
        this.pollDao = pollDao;
    }

    public Poll findById(int pollId){
        return pollDao.findById(pollId);
    }

    public Client getOwner(int pollId) {
        return pollDao.getOwner(pollId);
    }

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

    public void updateVote(int yes, int no, int pollId) {
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        int currentYes = poll.getYesVotes();
        int currentNo = poll.getNoVotes();
        poll.setYesVotes(currentYes+yes);
        poll.setNoVotes(currentNo+no);
        commit(poll);
    }

    public void updatePoll(int pollId, String name, String theme) {
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        if(name != null && !name.equals("")){
            poll.setName(name);
        }
        if(theme != null && !theme.equals("")){
            poll.setTheme(theme);
        }
        commit(poll);
    }

    public void closePoll(int pollId) {
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        poll.setActive(false);
        commit(poll);
    }

    public void openPoll(int pollId) {
        setUp();
        Poll poll = em.find(Poll.class,pollId);
        poll.setActive(true);
        commit(poll);
    }

    public void updateTime(int pollId, LocalDateTime startDate, LocalDateTime endDate) {
        setUp();
        TimeLimitPoll poll = em.find(TimeLimitPoll.class,pollId);
        if(poll != null) {
            poll.setStartDate(startDate);
            poll.setEndDate(endDate);
            commit(poll);
        }
    }

    public void createPoll(String name, String theme, boolean isPrivate, LocalDateTime createdDate, Client client) {
        setUp();
        Poll poll = new Poll(name,theme,isPrivate,createdDate,client);
        commit(poll);
    }

    public void createTimeLimitPoll(String name, String theme, boolean isPrivate, LocalDateTime createdDate, Client client, LocalDateTime startDate, LocalDateTime endDate) {
        setUp();
        TimeLimitPoll poll = new TimeLimitPoll(name,theme,isPrivate,createdDate,client,startDate,endDate);
        commit(poll);
    }

    public void deletePoll(int pollId) {
        setUp();
        em.remove(em.find(Poll.class,pollId));
        em.getTransaction().commit();
        em.close();
    }
}
