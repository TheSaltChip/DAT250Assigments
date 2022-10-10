package no.hvl.dat250.jpa.assignments.repository.user;

import no.hvl.dat250.jpa.assignments.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
}
