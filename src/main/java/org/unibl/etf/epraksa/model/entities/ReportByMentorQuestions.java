package org.unibl.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Report_By_Mentor_Questions")
public class ReportByMentorQuestions
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",
            nullable = false)
    private Integer id;

    @Basic
    @Column(name = "Question",
            length = 255)
    private String question;
}
