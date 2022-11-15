package no.hvl.dat250.assignment.api.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Time {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
