package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;

import java.util.List;

@Data
public class StudentQuestionnaireJSON {
    private Long id;
    private List<OneEntryForQuestionnaireJSON> input;
    private String mentorsComment;
}
