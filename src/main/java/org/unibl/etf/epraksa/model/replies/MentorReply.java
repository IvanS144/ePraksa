package org.unibl.etf.epraksa.model.replies;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class MentorReply {
    @Min(1)
    private Long id;
    @Min(1)
    private Long companyId;

    @NotBlank
    private String FirstName;
    @NotBlank
    private String LastName;
    @NotBlank
    private Boolean isCurrentMentor;

}
