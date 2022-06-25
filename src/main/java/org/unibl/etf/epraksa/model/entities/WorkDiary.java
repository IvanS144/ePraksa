package org.unibl.etf.epraksa.model.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "work_diary")
public class WorkDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkDiaryID",
            nullable = false)
    private Long workDiaryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "State",
            length = 45)
    private State state;

    @CreatedDate
    @Column(name = "CreatedAt",
            nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "LastModifiedDate",
            nullable = false)
    private LocalDate lastModifiedDate;

    @Basic
    @Column(name = "DeletedDate")
    private LocalDate deletedDate;

    @OneToOne(mappedBy="workDiary", fetch=FetchType.LAZY)
    private StudentHasInternship studentOnInternship;

    @OneToMany(mappedBy = "workDiary",
            fetch = FetchType.EAGER)
    private List<WorkDiaryEntry> workDiaryEntries;

}
