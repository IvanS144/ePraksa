package org.unibl.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "internship")
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InternshipID",
            nullable = false)
    private Long internshipId;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type",
            nullable = false,
            length = 7)
    private InternshipType type;

    @Basic
    @Column(name = "StartDate",
            nullable = false)
    private LocalDateTime startDate;

    @Basic
    @Column(name = "EndDAte")
    private LocalDateTime endDate;

    @Basic
    @Column(name = "Cycles",
            nullable = false)
    private String cycles;

    @Basic
    @Column(name = "Description",
            nullable = false)
    private String description;

    @Basic
    @Column(name = "Details",
            nullable = false)
    private String details;

    @Basic
    @Column(name = "Schedule",
            nullable = false)
    private String schedule;

    @Basic
    @Column(name = "Year",
            nullable = false)
    private Integer year;

    @Basic
    @Column(name = "WorkHours",
            nullable = false)
    private Integer workHours;

    @Basic
    @Column(name = "Field",
            nullable = false)
    private String field;

    @Basic
    @Column(name = "CvRequired",
            nullable = false)
    private Integer cvRequired;

    @Basic
    @Column(name = "LetterRequired",
            nullable = false)
    private Integer letterRequired;

    @Basic
    @Column(name = "Link",
            length = 128)
    private String link;

    @Basic
    @Column(name = "SubmissionDue",
            nullable = false)
    private LocalDateTime submissionDue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CompanyID",
            referencedColumnName = "ID",
            nullable = false)
    private Company company;

    @Basic
    @Column(name = "IsPublished",
            nullable = false)
    private Integer isPublished;

    @Basic
    @Column(name = "IsAccepted",
            nullable = false)
    private Boolean isAccepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MentorID",
            referencedColumnName = "ID",
            nullable = false)
    private Mentor mentor;

    @Basic
    @Column(name = "IsFinished",
            nullable = false)
    private Integer isFinished;

    @Basic
    @Column(name = "Courses",
            nullable = false)
    private String courses;

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

    @OneToMany(mappedBy = "internship",
            fetch = FetchType.LAZY)
    private List<StudentHasInternship> studentHasInternships;

}
