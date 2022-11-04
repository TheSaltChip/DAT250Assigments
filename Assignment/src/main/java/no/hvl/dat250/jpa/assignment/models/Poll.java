package no.hvl.dat250.jpa.assignment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "POLL", schema = "APP")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String question;

    @NonNull
    private String theme;

    @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Vote> votes;

    @NonNull
    private Boolean isPrivate;
    @NonNull
    @Enumerated(EnumType.STRING)
    private PollStatus activeStatus;
    @NonNull
    private LocalDateTime createdDate;

    @ManyToOne(targetEntity = User.class)
    @NonNull
    @JoinColumn(referencedColumnName = "username")
    @JsonBackReference(value = "owner")
    private User owner;

    @Version
    @JsonIgnore
    protected Integer version;

    public Poll(@NonNull String question, @NonNull String theme, @NonNull Boolean isPrivate, @NonNull User owner) {
        this.question = question;
        this.theme = theme;
        this.isPrivate = isPrivate;
        this.activeStatus = PollStatus.CLOSED;
        this.createdDate = LocalDateTime.now();
        this.owner = owner;
    }

    protected Poll() {
    }

    public void setActiveStatusToOpen(){
        this.activeStatus = PollStatus.OPEN;
    }

    public void setActiveStatusToFinished(){
        this.activeStatus = PollStatus.FINISHED;
    }

    public void setActiveStatusToClosed(){
        this.activeStatus = PollStatus.CLOSED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Poll poll = (Poll) o;

        if (!Objects.equals(id, poll.id)) return false;
        if (!question.equals(poll.question)) return false;
        if (!theme.equals(poll.theme)) return false;
        if (!Objects.equals(votes, poll.votes)) return false;
        if (!Objects.equals(isPrivate, poll.isPrivate)) return false;
        if (!Objects.equals(activeStatus, poll.activeStatus)) return false;
        if (!Objects.equals(createdDate, poll.createdDate)) return false;
        if (!owner.equals(poll.owner)) return false;
        return Objects.equals(version, poll.version);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + question.hashCode();
        result = 31 * result + theme.hashCode();
        result = 31 * result + (votes != null ? votes.hashCode() : 0);
        result = 31 * result + isPrivate.hashCode();
        result = 31 * result + activeStatus.hashCode();
        result = 31 * result + createdDate.hashCode();
        result = 31 * result + owner.hashCode();
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", name='" + question + '\'' +
                ", theme='" + theme + '\'' +
                ", votes=" + votes +
                ", isPrivate=" + isPrivate +
                ", activeStatus=" + activeStatus +
                ", createdDate=" + createdDate +
                ", owner=" + owner.getUsername() +
                '}';
    }
}