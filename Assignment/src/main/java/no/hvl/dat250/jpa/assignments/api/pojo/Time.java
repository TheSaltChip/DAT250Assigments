package no.hvl.dat250.jpa.assignments.api.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Time {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
