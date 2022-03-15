package org.unibl.etf.epraksa.model.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class WorkDiaryEntryRequest {

    private Long workDairyId;

    private Long entryId;

    @PastOrPresent
    private LocalDate date;

    @Min(1)
    private Integer day;

    private LocalTime from;

    private LocalTime to;

    @NotBlank
    private String text;

//    private Long previousId;
}
