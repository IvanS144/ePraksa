package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;
import org.unibl.etf.epraksa.model.entities.State;

@Data
public class StudentApplicationDTO
{
   String internshipName;
   String companyName;
   Long studentId;
   State state;
}
