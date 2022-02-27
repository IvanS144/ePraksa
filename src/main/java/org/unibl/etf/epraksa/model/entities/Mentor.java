package org.unibl.etf.epraksa.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
//@PrimaryKeyJoinColumn(name = "ID")
@Table(name = "mentor")
public class Mentor extends User{

//    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID",
//            nullable = false)
//    private Long id;

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

    @OneToMany(mappedBy = "mentor",
            fetch = FetchType.LAZY)
    private List<MentorNote> mentorNotes;


}
