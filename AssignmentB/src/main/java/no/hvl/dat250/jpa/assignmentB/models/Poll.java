package no.hvl.dat250.jpa.assignmentB.models;

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

    @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Votes> votes;

    private Boolean isPrivate;
    private Boolean active;
    private LocalDateTime createdDate;

    @ManyToOne(targetEntity = Client.class)
    @NonNull
    @JoinColumn(referencedColumnName = "username")
    @JsonBackReference
    private Client owner;

    @Version
    protected Integer version;

    public Poll(@NonNull String name, @NonNull String theme, Boolean isPrivate, LocalDateTime createdDate, @NonNull Client owner) {
        this.name = name;
        this.theme = theme;
        this.isPrivate = isPrivate;
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
        result = 31 * result + (isPrivate != null ? isPrivate.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + owner.hashCode();
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
