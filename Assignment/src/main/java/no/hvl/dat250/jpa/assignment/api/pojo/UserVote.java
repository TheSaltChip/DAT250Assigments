package no.hvl.dat250.jpa.assignment.api.pojo;

import lombok.Data;

@Data
public class UserVote {
    private String username;
    private boolean vote;
}