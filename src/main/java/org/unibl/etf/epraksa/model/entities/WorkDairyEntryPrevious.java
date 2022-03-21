package org.unibl.etf.epraksa.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "work_dairy_entry_previous")
public class WorkDairyEntryPrevious {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EntryID",
            nullable = false)
    private Long entryId;

    @Basic
    @Column(name = "Date",
            nullable = false)
    private LocalDate date;

    @Basic
    @Column(name = "Day",
            nullable = false)
    private Integer day;

    @Basic
    @Column(name = "From",
            nullable = false)
    private LocalTime from;

    @Basic
    @Column(name = "To",
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

    public WorkDairyEntryPrevious(WorkDairyEntry workDairyEntry){
        this.date = workDairyEntry.getDate();
        this.day = workDairyEntry.getDay();
        this.from = workDairyEntry.getFrom();
        this.to = workDairyEntry.getTo();
        this.text = workDairyEntry.getText();
        this.createdAt = workDairyEntry.getCreatedAt();
    }

}
