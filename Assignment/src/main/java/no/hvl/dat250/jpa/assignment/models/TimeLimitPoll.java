package no.hvl.dat250.jpa.assignment.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@EqualsAndHashCode(callSuper = true)
public class TimeLimitPoll extends Poll {
    @NonNull
    private LocalDateTime startDate;

    @NonNull
    private LocalDateTime endDate;

    public TimeLimitPoll(@NonNull String name, @NonNull String theme, @NonNull Boolean isPrivate,
                         @NonNull User owner, @NonNull LocalDateTime startDate, @NonNull LocalDateTime endDate) {
        super(name, theme, isPrivate, owner);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected TimeLimitPoll() {
        super();
    }
}
