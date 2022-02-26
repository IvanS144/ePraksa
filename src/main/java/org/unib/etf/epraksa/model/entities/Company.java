package org.unib.etf.epraksa.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
//@PrimaryKeyJoinColumn(name = "ID")
@Table(name = "company")
public class Company extends User{
//    @Id
//////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID",
//            nullable = false)
//    private Long id;
//
//    public Company(Long id) {
//        this.id = id;
//    }

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
