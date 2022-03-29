package org.unibl.etf.epraksa.model.dataTransferObjects;
import lombok.Data;
import org.unibl.etf.epraksa.model.entities.InternshipType;
import org.unibl.etf.epraksa.model.entities.State;

@Data
public class InternshipApplicationDTO
{
    private Long internshipId;
    private String studentFirstName;
    private String studentLastName;
    private State state;
    private String motivationalLetter;
    private InternshipType internshipType;
}
