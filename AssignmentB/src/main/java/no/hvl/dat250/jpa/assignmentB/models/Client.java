package no.hvl.dat250.jpa.assignmentB.models;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
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
        this.ownedPolls = new HashSet<>();
    }

    public Client() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!Objects.equals(username, client.username)) return false;
        if (!Objects.equals(email, client.email)) return false;
        if (!Objects.equals(firstname, client.firstname)) return false;
        if (!Objects.equals(lastname, client.lastname)) return false;
        if (role != client.role) return false;
        return password.equals(client.password);
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + role.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
