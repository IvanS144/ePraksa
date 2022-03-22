package org.unibl.etf.epraksa.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "work_dairy_entry")
@NoArgsConstructor
public class WorkDairyEntry implements Serializable {

    @EmbeddedId
    WorkDairyEntryPK id;

//    @MapsId("entryID")
//    @Id
//    @Column(name = "EntryID",
//            nullable = false)
//    private Long entryId;

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

    @Column(name = "FromTime",
            nullable = false)
    private LocalTime fromTime;

    @Basic
    @Column(name = "ToTime",
            nullable = false)
    private LocalTime toTime;

    @Basic
    @Column(name = "Text",
            nullable = false)
    private String text;

    @CreatedDate
    @Column(name = "CreatedAt",
            nullable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "LastModifiedDate",
            nullable = false)
    private LocalDate lastModifiedDate;

    @Basic
    @Column(name = "DeletedDate")
    private LocalDate deletedDate;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name="WorkDairyID", referencedColumnName="WorkDairyID"),
            @JoinColumn(name="EntryID", referencedColumnName="EntryID")
    })
    private WorkDairyEntryPrevious previousVersion;

    /*@OneToOne
    @JoinColumn(name = "PreviousVersionID",
            referencedColumnName = "EntryID")
    private WorkDairyEntryPrevious previousVersion;*/
}
