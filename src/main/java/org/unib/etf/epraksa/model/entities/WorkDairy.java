package org.unib.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
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

    @OneToMany(mappedBy = "workDairy")
    private List<WorkDairyEntry> workDairyEntries;

}
