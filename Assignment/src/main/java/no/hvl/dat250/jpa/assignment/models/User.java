package no.hvl.dat250.jpa.assignment.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "CLIENT", schema = "APP")
public class User {
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
    @JsonManagedReference(value = "owner")
    private Set<Poll> ownedPolls;

    public User(String username, @NonNull String password, @NonNull Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.ownedPolls = new HashSet<>();
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(firstname, user.firstname)) return false;
        if (!Objects.equals(lastname, user.lastname)) return false;
        if (role != user.role) return false;
        return password.equals(user.password);
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
