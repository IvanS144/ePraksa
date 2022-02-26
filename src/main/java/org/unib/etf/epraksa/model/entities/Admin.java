package org.unib.etf.epraksa.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "ID")
@Table(name = "admin")
@EqualsAndHashCode(callSuper = true)
public class Admin extends User{
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
}
