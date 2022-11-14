package no.hvl.dat250.jpa.assignment.repository.poll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
public class PollRepositoryTest {
    @Autowired
    private PollRepository pollRepository;

    @Test
    public void injectedDependencyIsNotNull() {
        assertThat(pollRepository, notNullValue());
    }
}
