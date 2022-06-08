package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;
import org.unibl.etf.epraksa.model.entities.InternshipType;
import org.unibl.etf.epraksa.model.entities.State;

@Data
public class StudentApplicationDTO
{
   private Long internshipId;
   private String internshipName;
   private String companyName;
   private Long studentId;
   private State state;
   private String motivationalLetter;
   private InternshipType internshipType;
   private String report;
}
