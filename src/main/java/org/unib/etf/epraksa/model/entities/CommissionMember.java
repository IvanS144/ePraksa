package org.unib.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "commision_member")
public class CommissionMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",
            nullable = false)
    private Long id;

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
    @Column(name = "Department",
            nullable = false,
            length = 45)
    private String department;

    @Basic
    @Column(name = "IsCurrentMember",
            nullable = false)
    private Integer isCurrentMember;

    @OneToMany(mappedBy = "commissionMember")
    private List<CommissionMemberNote> commissionMemberNotes;

}
