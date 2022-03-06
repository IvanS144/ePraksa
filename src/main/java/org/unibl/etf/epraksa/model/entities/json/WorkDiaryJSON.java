package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;
import org.unibl.etf.epraksa.model.entities.Student;

@Data
public class WorkDiaryJSON {
    private Long id;
    private Student student;
    private String indexNumber;
    private String studyProgram;
    private String mentorsNameSurname;
}
