package no.hvl.dat250.jpa.assignment.api.pojo;

import lombok.Data;

@Data
public class RoleString {
    private String role;

    public String get() {
        return role;
    }
}
