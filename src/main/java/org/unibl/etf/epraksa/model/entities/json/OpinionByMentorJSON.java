package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OpinionByMentorJSON {
    private Long id;
    private String mentor;
    private LocalDate periodOfInternshipFrom;
    private LocalDate periodOfInternshipUntil;
    private Integer numberOfDays;
    private Integer numberOfHours;
    private List<StudentObligationsJSON> obligations;
}
