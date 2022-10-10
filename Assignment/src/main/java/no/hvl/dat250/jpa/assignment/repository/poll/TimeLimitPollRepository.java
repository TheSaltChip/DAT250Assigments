package no.hvl.dat250.jpa.assignment.repository.poll;

import no.hvl.dat250.jpa.assignment.models.TimeLimitPoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeLimitPollRepository extends JpaRepository<TimeLimitPoll, Long> {
}
