package no.hvl.dat250.jpa.assignment.api.pojo;

import lombok.Data;

@Data
public class DeviceVotes {
    private String deviceId;
    private int yes;
    private int no;
}
