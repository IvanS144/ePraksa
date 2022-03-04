package org.unibl.etf.epraksa.model.requests;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.unibl.etf.epraksa.model.entities.InternshipType;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class InternshipRequest {
    
    @Min(1)
    private Long companyId;
    
    private InternshipType internshipType;

    @Future
    private LocalDate startDate;

    @Future
    private LocalDate endDate;

    @NotEmpty
    private String[] cycles;

    @NotBlank
    private String description;

    @NotBlank
    private String details;

    @NotBlank
    private String schedule;
    
    @Min(3)
    @Max(4)
    private Integer year;

    private Integer workHours;

    @NotBlank
    private String internshipField;
    private Boolean requiredCV;
    private Boolean requiredLetter;
    @URL
    private String link;

    @Future
    private LocalDate submissionDue;
    @Min(1)
    private Long mentorId;

    @NotEmpty
    private String[] courses;
}
