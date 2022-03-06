package org.unibl.etf.epraksa.model.entities.json;

import lombok.Data;

@Data
public class OpinionByMentor {
    private Long id;
    private String strucniMentor;
    private String periodTrajanjaPrakseOd;
    private String periodTrajanjaPrakseDo;
    private String brojDana;
    private String brojSati;
    private String[] obaveze;
}
