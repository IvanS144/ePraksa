package org.unibl.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "work_dairy_entry_previous")
public class WorkDairyEntryPrevious {

    @Id
    @Column(name = "EntryID",
            nullable = false)
    private Long entryId;

    @Basic
    @Column(name = "Date",
            nullable = false)
    private LocalDateTime date;

    @Basic
    @Column(name = "Day",
            nullable = false)
    private Integer day;

    @Basic
    @Column(name = "From",
            nullable = false)
    private Time from;

    @Basic
    @Column(name = "To",
            nullable = false)
    private Time to;

    @Basic
    @Column(name = "Text",
            nullable = false)
    private String text;

    @Basic
    @Column(name = "CreatedAt",
            nullable = false)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "LastModifiedDate",
            nullable = false)
    private LocalDateTime lastModifiedDate;

    @Basic
    @Column(name = "DeletedDate")
    private LocalDateTime deletedDate;

}
