package no.hvl.dat250.jpa.assignmentB.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String name;

    @NonNull
    private String theme;

    @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Vote> votes;

    @NonNull
    private Boolean isPrivate;
    @NonNull
    private Boolean active;
    @NonNull
    private LocalDateTime createdDate;

    @ManyToOne(targetEntity = User.class)
    @NonNull
    @JoinColumn(referencedColumnName = "username")
    @JsonBackReference
    private User owner;

    @Version
    protected Integer version;

    public Poll(@NonNull String name, @NonNull String theme, @NonNull Boolean isPrivate, @NonNull Boolean active, @NonNull LocalDateTime createdDate, @NonNull User owner) {
        this.name = name;
        this.theme = theme;
        this.isPrivate = isPrivate;
        this.active = active;
        this.createdDate = createdDate;
        this.owner = owner;
    }

    protected Poll() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Poll poll = (Poll) o;

        if (!Objects.equals(id, poll.id)) return false;
        if (!name.equals(poll.name)) return false;
        if (!theme.equals(poll.theme)) return false;
        if (!Objects.equals(votes, poll.votes)) return false;
        if (!Objects.equals(isPrivate, poll.isPrivate)) return false;
        if (!Objects.equals(active, poll.active)) return false;
        if (!Objects.equals(createdDate, poll.createdDate)) return false;
        if (!owner.equals(poll.owner)) return false;
        return Objects.equals(version, poll.version);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + theme.hashCode();
        result = 31 * result + (votes != null ? votes.hashCode() : 0);
        result = 31 * result + isPrivate.hashCode();
        result = 31 * result + active.hashCode();
        result = 31 * result + createdDate.hashCode();
        result = 31 * result + owner.hashCode();
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", theme='" + theme + '\'' +
                ", votes=" + votes +
                ", isPrivate=" + isPrivate +
                ", active=" + active +
                ", createdDate=" + createdDate +
                ", owner=" + owner.getUsername() +
                '}';
    }
}