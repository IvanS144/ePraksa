package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;

@Data
public class OneInputWorkDiaryJSON {
    private Long id;
    private String datum;
    private String dan;
    private String od;
//    ne moze ici do
    private String ddo;
    private String opis;
}
