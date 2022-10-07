package no.hvl.dat250.jpa.assignmentB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class Main {
    public static final String PERSISTENCE_UNIT_NAME = "assignmentB";

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
