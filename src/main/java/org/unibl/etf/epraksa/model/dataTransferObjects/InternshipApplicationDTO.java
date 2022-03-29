package org.unibl.etf.epraksa.model.dataTransferObjects;
import lombok.Data;
import org.unibl.etf.epraksa.model.entities.State;

@Data
public class InternshipApplicationDTO
{
    Long internshipId;
    String studentFirstName;
    String studentLastName;
    State state;
}
