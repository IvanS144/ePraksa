package org.unib.etf.epraksa.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "ID")
@Table(name = "commision_member")
@EqualsAndHashCode(callSuper = true)
public class CommissionMember extends User{

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

    @OneToMany(mappedBy = "commissionMember",
            fetch = FetchType.LAZY)
    private List<CommissionMemberNote> commissionMemberNotes;

}
