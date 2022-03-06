package org.unibl.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cv")
public class CV {

    @Id
    @Column(name = "ID",
            nullable = false)
    private Long id;

    @Basic
    @Column(name = "PortfolioLink",
            length = 45)
    private String porfolioLink;

    @Basic
    @Column(name = "Skills")
    private String skills;

    @Basic
    @Column(name = "Languages")
    private String languages;

    @Basic
    @Column(name = "Hobbies")
    private String hobbies;

    @Basic
    @Column(name = "Introduction")
    private String introduction;

    @Basic
    @Column(name = "WorkExperience")
    private String workExperience;

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
