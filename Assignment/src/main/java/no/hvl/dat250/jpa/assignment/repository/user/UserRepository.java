package no.hvl.dat250.jpa.assignment.repository.user;

import no.hvl.dat250.jpa.assignment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findByUsername(String userName);
}
