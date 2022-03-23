package org.unibl.etf.epraksa.model.requests;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ApplicationRequest {

    @Min(1)
    private Long internshipId;
    @Min(1)
    private Long studentId;
    private String motivationalLetter;
}
