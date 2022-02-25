package org.unib.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "company")
public class Company {
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

    @OneToMany(mappedBy = "company")
    private List<Mentor> mentors;
}
