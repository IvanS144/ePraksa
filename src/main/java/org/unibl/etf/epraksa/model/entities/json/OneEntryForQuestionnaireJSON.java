package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;

@Data
public class OneEntryForQuestionnaireJSON {
    private Integer id;
    private String question;
    private AnswerToTheQuestionnaireENUM answer;
}
