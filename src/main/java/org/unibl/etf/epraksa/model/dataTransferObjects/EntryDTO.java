package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EntryDTO {
    private Long workDiaryId;
    private Long entryId;
    private LocalDate date;
    private Integer day;
    private LocalTime from;
    private LocalTime to;
    private String text;
    private Long previousVersionId;
}
