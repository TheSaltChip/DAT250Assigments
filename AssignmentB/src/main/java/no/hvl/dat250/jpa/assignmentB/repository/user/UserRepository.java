package no.hvl.dat250.jpa.assignmentB.repository.user;

import no.hvl.dat250.jpa.assignmentB.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
}
