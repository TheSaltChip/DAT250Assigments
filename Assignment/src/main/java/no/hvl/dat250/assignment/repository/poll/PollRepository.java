package no.hvl.dat250.assignment.repository.poll;

import no.hvl.dat250.assignment.models.poll.Poll;
import no.hvl.dat250.assignment.models.poll.PollStatus;
import no.hvl.dat250.assignment.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> findAllByActiveStatus(PollStatus status);

    List<Poll> findAllByActiveStatusAndIsPrivate(PollStatus status, boolean isPrivate);

    List<Poll> findAllByOwner(User owner);


    Optional<Poll> findByCode(Integer code);

    Boolean existsPollByCode(Integer code);
}
