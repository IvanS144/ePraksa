package org.unibl.etf.epraksa.model.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "student_has_internship")
public class StudentHasInternship implements Serializable{
    @EmbeddedId StudentHasInternshipPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    @JoinColumn(name = "ID",
            referencedColumnName = "ID",
            nullable = false)
    private Student student;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("internshipId")
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

}
