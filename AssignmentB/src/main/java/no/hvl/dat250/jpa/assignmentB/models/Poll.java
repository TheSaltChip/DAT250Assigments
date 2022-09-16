package no.hvl.dat250.jpa.assignmentB.models;

import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "POLL", schema = "APP")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String theme;

    private Integer yesVotes;
    private Integer noVotes;

    private Boolean isPrivate;
    private LocalDateTime createdDate;

    @ManyToOne(targetEntity = Client.class)
    @NonNull
    @JoinColumn(referencedColumnName = "username")
    private Client owner;

    @Version
    protected Integer version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getYesVotes() {
        return yesVotes;
    }

    public void setYesVotes(Integer yesVotes) {
        this.yesVotes = yesVotes;
    }

    public Integer getNoVotes() {
        return noVotes;
    }

    public void setNoVotes(Integer noVotes) {
        this.noVotes = noVotes;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }
}
