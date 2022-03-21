package org.unibl.etf.epraksa.model.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class WorkDiaryEntryRequest {

    @PastOrPresent
    private LocalDate date;

    @Min(1)
    private Integer day;

    private String fromTime;

    private String toTime;

    @NotBlank
    private String text;

//    private Long previousId;
}
