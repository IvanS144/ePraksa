package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;

@Data
public class OneEntryWorkDiaryJSON {
    private Long id;
    private String date;
    private String day;
    private String from;
    private String until;
    private String description;
}
