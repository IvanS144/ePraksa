package org.unib.etf.epraksa.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "ID")
@Table(name = "student")
public class Student extends User{

    @Id
    @Column(name = "ID",
            nullable = false)
    private Long id;

    public Student(Long id) {
        this.id = id;
    }

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
    @Column(name = "Jmbg",
            nullable = false,
            length = 13)
    private String jmbg;

    @Basic
    @Column(name = "Index",
            nullable = false,
            length = 15)
    private String index;

    @Basic
    @Column(name = "BirthDate",
            nullable = false)
    private LocalDateTime birthDate;

    @Basic
    @Column(name = "Faculty",
            nullable = false,
            length = 45)
    private String faculty;

    @Basic
    @Column(name = "Year",
            nullable = false)
    private Integer year;

    @Basic
    @Column(name = "Course",
            nullable = false,
            length = 45)
    private String course;

    @Basic
    @Column(name = "Cycle",
            nullable = false,
            length = 45)
    private String cycle;

    @OneToMany(mappedBy = "student",
            fetch = FetchType.LAZY)
    private List<StudentHasInternship> studentHasInternships;
}
