package no.hvl.dat250.jpa.assignmentB.api.pojo;

import lombok.Data;

@Data
public class DeviceVotes {
    private String deviceId;
    private int yes;
    private int no;
}
