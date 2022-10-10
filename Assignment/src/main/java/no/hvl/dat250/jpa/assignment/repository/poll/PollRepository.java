package no.hvl.dat250.jpa.assignment.repository.poll;

import no.hvl.dat250.jpa.assignment.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
}
