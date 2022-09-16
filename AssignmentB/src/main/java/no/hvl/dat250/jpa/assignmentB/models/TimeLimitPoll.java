package no.hvl.dat250.jpa.assignmentB.models;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class TimeLimitPoll extends Poll {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
