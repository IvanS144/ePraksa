package org.unib.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "mentor")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",
            nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CompanyID",
            referencedColumnName = "ID",
            nullable = false)
    private Company company;

    @Basic
    @Column(name = "FirstName",
            nullable = false,
            length = 45)
    private String firstName;

    @Basic
    @Column(name = "LastName",
            nullable = false,
            length = 45)
    private String lastName;

    @Basic
    @Column(name = "IsCurrentMentor",
            nullable = false)
    private Integer isCurrentMentor;

    @OneToMany(mappedBy = "mentor")
    private List<MentorNote> mentorNotes;


}
