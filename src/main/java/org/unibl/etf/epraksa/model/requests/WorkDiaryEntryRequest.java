package org.unibl.etf.epraksa.model.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonFormat(pattern = "HH:mm:ss[.SSS][.SS][.S]", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime fromTime;

    @JsonFormat(pattern = "HH:mm:ss[.SSS][.SS][.S]", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime toTime;

    @NotBlank
    private String text;

//    private Long previousId;
}