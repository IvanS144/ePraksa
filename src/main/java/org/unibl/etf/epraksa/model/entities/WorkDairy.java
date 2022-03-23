package org.unibl.etf.epraksa.model.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "work_dairy")
public class WorkDairy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkDairyID",
            nullable = false)
    private Long workDairyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE",
            length = 45)
    private State state;

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

    @OneToOne(mappedBy="workDairy")
    private StudentHasInternship studentOnInternship;

    @OneToMany(mappedBy = "workDairy",
            fetch = FetchType.LAZY)
    private List<WorkDairyEntry> workDairyEntries;

}
