package org.unibl.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name="Mentor_note")
public class MentorNote {

    @Id
    @Column(name = "MentorNoteId",
            nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mentorNoteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MentorID",
            referencedColumnName = "ID",
            nullable = false)
    private Mentor mentor;

    @Basic
    @Column(name = "Text")
    private String text;

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
