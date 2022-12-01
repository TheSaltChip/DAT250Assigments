package no.hvl.dat250.assignment.persistence.repository.poll;

import no.hvl.dat250.assignment.persistence.models.poll.TimeLimitPoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeLimitPollRepository extends JpaRepository<TimeLimitPoll, Long> {
}
