package no.hvl.dat250.jpa.assignment.repository.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import no.hvl.dat250.jpa.assignment.models.vote.DeviceVote;

import java.util.UUID;

public interface DeviceVoteRepository extends JpaRepository<DeviceVote, UUID> {
}
