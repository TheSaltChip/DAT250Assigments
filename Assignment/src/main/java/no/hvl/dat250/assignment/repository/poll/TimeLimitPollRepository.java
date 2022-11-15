package no.hvl.dat250.assignment.repository.poll;

import no.hvl.dat250.assignment.models.poll.TimeLimitPoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeLimitPollRepository extends JpaRepository<TimeLimitPoll, Long> {
}
