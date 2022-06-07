package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EntryDTO {
    //private Long workDiaryId;
    private Long entryId;
    //private LocalDate date;
    private Integer day;
    private String fromTime;
    private String toTime;
    private String text;
    private LocalDate createdAt;
    private LocalDate lastModifiedDate;
    //private Long previousVersionId;
}
