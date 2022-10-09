package no.hvl.dat250.jpa.assignmentB.repository.poll;

import no.hvl.dat250.jpa.assignmentB.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
}
