package org.unibl.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "work_dairy_entry")
public class WorkDairyEntry implements Serializable {

    @EmbeddedId
    WorkDairyEntryPK id;

    @MapsId("entryID")
    @Column(name = "EntryID",
            nullable = false)
    private Long entryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workDairyID")
    @JoinColumn(name = "WorkDairyID",
            referencedColumnName = "WorkDairyID",
            nullable = false)
    private WorkDairy workDairy;

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

    @OneToOne
    @JoinColumn(name = "PreviousVersionID",
            referencedColumnName = "EntryID")
    private WorkDairyEntryPrevious previousVersion;
}
