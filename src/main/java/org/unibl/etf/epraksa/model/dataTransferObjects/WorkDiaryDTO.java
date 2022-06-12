package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;
import org.unibl.etf.epraksa.model.entities.State;

import java.util.List;

@Data
public class WorkDiaryDTO
{
    private Long workDiaryId;
    private State state;
    //private LocalDate createdAt;
   // private LocalDate lastModifiedDate;
    //private LocalDate deletedDate;
    private String studentFullName;
    private String studentIndex;
    private String studentCourse;
    private String mentorFullName;
    private Integer workedHours;
    private String internshipName;
    private List<EntryDTO> workDiaryEntries;
}
