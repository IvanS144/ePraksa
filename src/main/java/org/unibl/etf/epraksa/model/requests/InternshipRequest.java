package org.unibl.etf.epraksa.model.requests;

import lombok.Data;
import org.unibl.etf.epraksa.model.entities.InternshipType;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
public class InternshipRequest {
    @Min(1)
    private Long internshipId;
    @Min(1)
    private Long companyId;
    private InternshipType internshipType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String[] cycles;
    private String description;
    private String details;
    private String schedule;
    @Min(3)
    @Max(4)
    private Integer year;
    private Integer workHours;
    private String internshipField;
    private Boolean requiredCV;
    private Boolean requiredLetter;
    private String link;
    private LocalDateTime submissionDue;
    private Boolean isPublished;
    private Long mentorId;
    private String[] courses;
}
