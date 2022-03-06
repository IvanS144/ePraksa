package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;

@Data
public class OpinionByMentor {
    private Long id;
    private String mentor;
    private String periodOfInternshipFrom;
    private String periodOfInternshipUntil;
    private String numberOfDays;
    private String numberOfHours;
    private String[] obligations;
}
