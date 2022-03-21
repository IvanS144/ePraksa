package org.unibl.etf.epraksa.model.dataTransferObjects;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class WorkDiaryRecordDTO {
    private LocalDate date;
    private Integer day;
    private LocalTime fromTime;
    private LocalTime toTime;
    private String text;
}
