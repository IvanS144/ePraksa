package org.unibl.etf.epraksa.model.requests;

import lombok.Data;
import org.unibl.etf.epraksa.model.entities.InternshipType;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class InternshipRequest {
    
    @Min(1)
    private Long companyId;
    
    private InternshipType internshipType;
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
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
    
    private LocalDate submissionDue;
    @Min(1)
    private Long mentorId;
    
    private String[] courses;
}
