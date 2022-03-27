package org.unibl.etf.epraksa.model.replies;

import lombok.Data;
import org.unibl.etf.epraksa.model.dataTransferObjects.EntryDTO;
import org.unibl.etf.epraksa.model.entities.State;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntry;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class WorkDairyReply {
    private Long workDairyId;
    private State state;
    //private LocalDate createdAt;
   // private LocalDate lastModifiedDate;
    //private LocalDate deletedDate;
    private String studentFullName;
    private String studentIndex;
    private String studentCourse;
    private String mentorFullName;
    private Integer workedHours;
    private List<EntryDTO> workDairyEntries;
}
