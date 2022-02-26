package org.unib.etf.epraksa.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "ID")
@Table(name = "company")
@EqualsAndHashCode(callSuper = true)
public class Company extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",
            nullable = false)
    private Long id;

    @Basic
    @Column(name = "Name",
            nullable = false,
            length = 45)
    private String name;

    @Basic
    @Column(name = "Link",
            length = 128)
    private String link;

    @OneToMany(mappedBy = "company",
            fetch = FetchType.LAZY)
    private List<Mentor> mentors;
}
