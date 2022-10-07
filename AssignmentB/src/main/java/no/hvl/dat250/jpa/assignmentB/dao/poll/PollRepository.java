package no.hvl.dat250.jpa.assignmentB.dao.poll;

import no.hvl.dat250.jpa.assignmentB.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
}
