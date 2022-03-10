package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;

@Data
public class WorkExperienceEntryJSON {
    private String period;
    private String company;
    private String position;
    private String description;
}
