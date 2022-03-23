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
@Table(name = "application")
public class Application implements Serializable{

    @EmbeddedId
    ApplicationPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("internshipId")
    @JoinColumn(name = "InternshipID",
            referencedColumnName = "InternshipID",
            nullable = false)
    private Internship internship;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
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
    @Column(name = "MotivationalLetter")
    private String motivationLetter;

    @Basic
    @Column(name = "InternshipStars")
    private Integer internshipStars;

    @Basic
    @Column(name = "ReviewByStudent")
    private String reviewByStudent;


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

}
