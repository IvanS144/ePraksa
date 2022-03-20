package org.unibl.etf.epraksa.model.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "internship")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InternshipID",
            nullable = false)
    private Long internshipId;

    @Basic
    @Column(name="Title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type",
            nullable = false,
            length = 7)
    private InternshipType internshipType;

    @Basic
    @Column(name = "StartDate",
            nullable = false)
    private LocalDate startDate;

    @Basic
    @Column(name = "EndDAte")
    private LocalDate endDate;
    @Type(type="json")

    @Column(name = "Cycles", columnDefinition = "json")
    private String[] cycles;

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

    @Type(type="json")
    @Column(name = "Years",
            nullable = false)
    private Integer[] years;

    @Basic
    @Column(name = "WorkHours")
    private Integer workHours;

    @Basic
    @Column(name = "Field",
            nullable = false)
    private String internshipField;

    @Basic
    @Column(name="City", nullable=false)
    private String city;

    @Basic
    @Column(name = "CvRequired",
            nullable = false)
    private Boolean requiredCV;

    @Basic
    @Column(name = "LetterRequired",
            nullable = false)
    private Boolean requiredLetter;

    @Basic
    @Column(name = "Link",
            length = 128)
    private String link;

    @Basic
    @Column(name = "SubmissionDue",
            nullable = false)
    private LocalDate submissionDue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CompanyID",
            referencedColumnName = "ID",
            nullable = false)
    private Company company;

    @Basic
    @Column(name = "IsPublished",
            nullable = false)
    private Boolean isPublished;

    @Basic
    @Column(name = "IsAccepted")
    private Boolean isAccepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MentorID",
            referencedColumnName = "ID",
            nullable = false)
    private Mentor mentor;

    @Basic
    @Column(name = "IsFinished",
            nullable = false)
    private Boolean isFinished;

    @Type(type="json")
    @Column(name = "Courses",
            nullable = false, columnDefinition = "json")
    private String[] courses;

    @Column(name = "CreatedAt",
            nullable = false)
    @CreatedDate
    private LocalDate createdAt;

    @Column(name = "LastModifiedDate",
            nullable = false)
    @LastModifiedDate
    private LocalDate lastModifiedDate;

    @Basic
    @Column(name = "DeletedDate")
    private LocalDate deletedDate;

    @OneToMany(mappedBy = "internship",
            fetch = FetchType.LAZY)
    private List<StudentHasInternship> studentHasInternships;

}
