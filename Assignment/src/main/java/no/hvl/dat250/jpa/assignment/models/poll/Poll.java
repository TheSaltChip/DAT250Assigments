package no.hvl.dat250.jpa.assignment.models.poll;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NonNull;
import no.hvl.dat250.jpa.assignment.models.user.User;
import no.hvl.dat250.jpa.assignment.models.vote.UserVote;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "POLL", schema = "APP")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer code;

    @NonNull
    private String question;

    @NonNull
    private String theme;

    private Integer noVotes;

    private Integer yesVotes;

    /*
    @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserVote> userVotes;
    */
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
        this.yesVotes = 0;
        this.noVotes = 0;
        this.isPrivate = isPrivate;
        this.activeStatus = PollStatus.CLOSED;
        this.createdDate = LocalDateTime.now();
        this.owner = owner;
    }

    protected Poll() {
    }

    public void setActiveStatusToOpen() {
        this.activeStatus = PollStatus.OPEN;
    }

    public void setActiveStatusToFinished() {
        this.activeStatus = PollStatus.FINISHED;
    }

    public void setActiveStatusToClosed() {
        this.activeStatus = PollStatus.CLOSED;
    }

    public void incYesVotes() {
        this.yesVotes++;
    }

    public void incNoVotes() {
        this.noVotes++;
    }
    public void addYesVotes(int amount){
        this.yesVotes += amount;
    }
    public void addNoVotes(int amount){
        this.noVotes += amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Poll poll = (Poll) o;

        if (!Objects.equals(id, poll.id)) return false;
        if (!code.equals(poll.code)) return false;
        if (!question.equals(poll.question)) return false;
        if (!theme.equals(poll.theme)) return false;
        if (!yesVotes.equals(poll.yesVotes)) return false;
        if (!noVotes.equals(poll.noVotes)) return false;
        if (!Objects.equals(isPrivate, poll.isPrivate)) return false;
        if (!Objects.equals(activeStatus, poll.activeStatus)) return false;
        if (!Objects.equals(createdDate, poll.createdDate)) return false;
        if (!owner.equals(poll.owner)) return false;
        return Objects.equals(version, poll.version);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + code.hashCode();
        result = 31 * result + question.hashCode();
        result = 31 * result + theme.hashCode();
        result = 31 * result + yesVotes.hashCode();
        result = 31 * result + noVotes.hashCode();
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
                ", code='" + code + '\'' +
                ", question='" + question + '\'' +
                ", theme='" + theme + '\'' +
                ", noVotes=" + noVotes +
                ", yesVotes=" + yesVotes +
                ", isPrivate=" + isPrivate +
                ", activeStatus=" + activeStatus +
                ", createdDate=" + createdDate +
                ", owner=" + owner.getUsername() +
                ", version=" + version +
                '}';
    }
}