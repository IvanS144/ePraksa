package org.unib.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appliacion")
public class Appliacion implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InternshipID",
            referencedColumnName = "InternshipID",
            nullable = false)
    private Internship internship;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID",
            referencedColumnName = "ID",
            nullable = false)
    private Student student;

    @Basic
    @Column(name = "State",
            nullable = false,
            length = 45)
    private String state;

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
