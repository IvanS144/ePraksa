package org.unib.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appliacion")
@IdClass(AppliacionPK.class)
public class Appliacion{

    @Id
    @Column(name = "InternshipID")
    private Long internshipId;

    @Id
    @Column(name = "StudentID")
    private Long studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InternshipID",
            referencedColumnName = "InternshipID",
            nullable = false)
    private Internship internship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID",
            referencedColumnName = "ID",
            nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(name = "State",
            nullable = false,
            length = 45)
    private State state;

    @Basic
    @Column(name = "Report")
    private String report;

    @Basic
    @Column(name = "MotivationLetter")
    private String motivationLetter;

    @Basic
    @Column(name = "InternshipStars")
    private Integer internshipStars;

    @Basic
    @Column(name = "ReviewByStudent")
    private String reviewByStudent;

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
