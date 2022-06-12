package org.unibl.etf.epraksa.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "work_diary_entry")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class WorkDiaryEntry implements Serializable {

    @EmbeddedId
    WorkDiaryEntryPK id;

//    @MapsId("entryID")
//    @Id
//    @Column(name = "EntryID",
//            nullable = false)
//    private Long entryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workDairyID")
    @JoinColumn(name = "WorkDiaryID",
            referencedColumnName = "WorkDiaryID",
            nullable = false)
    private WorkDiary workDiary;

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
            @JoinColumn(name="WorkDiaryID", referencedColumnName="WorkDiaryID"),
            @JoinColumn(name="EntryID", referencedColumnName="EntryID")
    })
    private WorkDiaryEntryPrevious previousVersion;

    /*@OneToOne
    @JoinColumn(name = "PreviousVersionID",
            referencedColumnName = "EntryID")
    private WorkDairyEntryPrevious previousVersion;*/
}
