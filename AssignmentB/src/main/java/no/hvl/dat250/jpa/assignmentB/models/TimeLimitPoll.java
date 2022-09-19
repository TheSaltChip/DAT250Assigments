package no.hvl.dat250.jpa.assignmentB.models;

import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class TimeLimitPoll extends Poll {
    @NonNull
    private LocalDateTime startDate;

    @NonNull
    private LocalDateTime endDate;

    public TimeLimitPoll(@NonNull String name, @NonNull String theme, Boolean isPrivate, LocalDateTime createdDate,
                         @NonNull Client owner, LocalDateTime startDate, LocalDateTime endDate) {
        super(name, theme, isPrivate, createdDate, owner);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected TimeLimitPoll() {
        super();
    }

    public LocalDateTime getStartDate(){
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate){
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate(){
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate){
        this.endDate = endDate;
    }
}
