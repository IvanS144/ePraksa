package org.unib.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "student_has_internship")
public class StudentHasInternship implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID",
            referencedColumnName = "ID",
            nullable = false)
    private Student student;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InternshipID",
            referencedColumnName = "InternshipID",
            nullable = false)
    private Internship internship;

    @OneToOne
    @JoinColumn(name = "WorkDairyID",
            referencedColumnName = "WorkDairyID",
            nullable = false)
    private WorkDairy workDairy;

    @OneToOne
    @JoinColumn(name = "ReportID", referencedColumnName = "ReportID")
    private ReportByMentor report;

    @OneToOne
    @JoinColumn(name = "GradesID", referencedColumnName = "GradesID")
    private GradesByMentor grades;

    @OneToOne
    @JoinColumn(name = "CommisionMemberNoteID", referencedColumnName = "CommisionMemberNoteID")
    private CommissionMemberNote commissionMemberNote;

    @OneToOne
    @JoinColumn(name = "MentorNoteId", referencedColumnName = "MentorNoteId")
    private MentorNote mentorNote;

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
