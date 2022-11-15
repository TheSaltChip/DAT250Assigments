package no.hvl.dat250.assignment.api.pojo;

import lombok.Data;

@Data
public class RoleString {
    private String role;

    public String get() {
        return role;
    }
}
