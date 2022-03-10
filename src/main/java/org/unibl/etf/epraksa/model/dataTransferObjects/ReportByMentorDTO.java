package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;
import org.unibl.etf.epraksa.model.entities.json.OpinionByMentorJSON;

import java.time.LocalDate;

@Data
public class ReportByMentorDTO {
    private Long reportId;
    private OpinionByMentorJSON opinionJSON;
    private LocalDate createdAt;
    private LocalDate lastModifiedDate;
    private LocalDate deletedDate;

}
