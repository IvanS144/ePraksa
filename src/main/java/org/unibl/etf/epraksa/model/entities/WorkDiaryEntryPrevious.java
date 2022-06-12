package org.unibl.etf.epraksa.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "work_dairy_entry_previous")
public class WorkDiaryEntryPrevious implements Serializable {

    @EmbeddedId
    WorkDiaryEntryPK Id;

    @Basic
    @Column(name = "Date",
            nullable = false)
    private LocalDate date;

    @Basic
    @Column(name = "Day",
            nullable = false)
    private Integer day;

    @Basic
    @Column(name = "FromTime",
            nullable = false)
    private LocalTime from;

    @Basic
    @Column(name = "ToTime",
            nullable = false)
    private LocalTime to;

    @Basic
    @Column(name = "Text",
            nullable = false)
    private String text;

    @Basic
    @Column(name = "CreatedAt",
            nullable = false)
    private LocalDate createdAt;

    @Basic
    @Column(name = "LastModifiedDate",
            nullable = false)
    private LocalDate lastModifiedDate;

    @Basic
    @Column(name = "DeletedDate")
    private LocalDate deletedDate;

    public WorkDiaryEntryPrevious(WorkDiaryEntry workDiaryEntry){
        this.Id = new WorkDiaryEntryPK(workDiaryEntry.getId().getWorkDairyID(), workDiaryEntry.getId().getEntryID());
        this.date = workDiaryEntry.getDate();
        this.day = workDiaryEntry.getDay();
        this.from = workDiaryEntry.getFromTime();
        this.to = workDiaryEntry.getToTime();
        this.text = workDiaryEntry.getText();
        this.createdAt = workDiaryEntry.getCreatedAt();
        this.lastModifiedDate = workDiaryEntry.getLastModifiedDate();
    }

}
