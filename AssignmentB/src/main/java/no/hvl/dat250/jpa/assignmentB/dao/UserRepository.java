package no.hvl.dat250.jpa.assignmentB.dao;

import no.hvl.dat250.jpa.assignmentB.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Client, String>, UserRepositoryCustom {
}
