package no.hvl.dat250.jpa.assignmentB.models;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "CLIENT", schema = "APP")
public class Client {
    @Id
    private String username;

    private String email;
    private String firstname;
    private String lastname;

    @Enumerated(value = EnumType.STRING)
    @NonNull
    private Role role;

    @NonNull
    private String password;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Poll> ownedPolls;

    public Client(String username, @NonNull String password, @NonNull Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Client() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Poll> getOwnedPolls() {
        return ownedPolls;
    }

    public void setOwnedPolls(Set<Poll> ownedPolls) {
        this.ownedPolls = ownedPolls;
    }
}
