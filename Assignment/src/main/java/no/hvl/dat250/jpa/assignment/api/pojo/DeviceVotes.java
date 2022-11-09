package no.hvl.dat250.jpa.assignment.api.pojo;

import lombok.Data;

import java.util.UUID;

@Data
public class DeviceVotes {
    private UUID deviceId;
    private Long pollId;
    private int yes;
    private int no;
}
