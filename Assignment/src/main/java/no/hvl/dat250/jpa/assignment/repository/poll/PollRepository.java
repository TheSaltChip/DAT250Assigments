package no.hvl.dat250.jpa.assignment.repository.poll;

import no.hvl.dat250.jpa.assignment.models.poll.Poll;
import no.hvl.dat250.jpa.assignment.models.poll.PollStatus;
import no.hvl.dat250.jpa.assignment.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> findAllByActiveStatus(PollStatus status);

    List<Poll> findAllByActiveStatusAndIsPrivate(PollStatus status, boolean isPrivate);

    List<Poll> findAllByOwner(User owner);
}
