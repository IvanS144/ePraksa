package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;

@Data
public class StudentObligationsJSON {
    private String serialNumber;
    private String descriptionOfTheObligation;
    private Long id;
}
