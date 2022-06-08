package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;

import java.util.List;

@Data
public class StudentQuestionnaireJSON {
    private List<OneEntryForQuestionnaireJSON> input;
    private String mentorsComment;

    public OneEntryForQuestionnaireJSON getEntryById(Integer id)
    {
        return input.stream().filter(entry -> entry.getId().equals(id)).findFirst().get();
    }
}
