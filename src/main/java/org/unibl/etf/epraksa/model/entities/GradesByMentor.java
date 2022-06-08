package org.unibl.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "grades_by_mentor")
public class GradesByMentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GradesID",
            nullable = false)
    private Long gradesId;

    @Basic
    @Column(name = "TechnicalKnowledge",
            nullable = false)
    private Integer technicalKnowledge;

    @Basic
    @Column(name = "Responsibility",
            nullable = false)
    private Integer resopnsibility;

    @Basic
    @Column(name = "Teamwork",
            nullable = false)
    private Integer teamwork;

    @Basic
    @Column(name = "Effort",
            nullable = false)
    private Integer effort;

    @Basic
    @Column(name = "Comment",
            nullable = false)
    private String comment;

    @Basic
    @Column(name = "CreatedAt",
            nullable = false)
    private LocalDate createdAt;

    @Basic
    @Column(name = "LastModifiedDate",
            nullable = false)
    private LocalDate lastModifiedDate;

    @Basic
    @Column(name = "DeletedDate")
    private LocalDate deletedDate;

}
