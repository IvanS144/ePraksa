package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;
import org.unibl.etf.epraksa.model.entities.State;

@Data
public class ApplicationDTO {
   Long internshipId;
   Long studentId;
   State state;
}
