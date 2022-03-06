package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;

@Data
public class StudentQuestionnaireJSON {
    private Long id;
    private String[] input;
    private String mentorsComment;
}
