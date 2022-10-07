package no.hvl.dat250.jpa.assignmentB.dao.poll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@DataJpaTest
public class PollRepositoryTest {
    @Autowired
    private PollRepository pollRepository;

    @Test
    public void injectedDependencyIsNotNull(){
        assertThat(pollRepository, notNullValue());
    }
}
