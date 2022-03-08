package org.unibl.etf.epraksa.model.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.unibl.etf.epraksa.model.entities.json.OpinionByMentorJSON;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "report_by_mentor")
@TypeDefs(@TypeDef(name = "json", typeClass = JsonType.class))
public class ReportByMentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReportID",
            nullable = false)
    private Long reportId;

    @Type(type = "json")
    @Column(name = "OpinionJSON",
            nullable = false,
            columnDefinition = "json")
    private OpinionByMentorJSON opinionJSON;

//    @Basic
//    @Column(name = "GradingJSON")
//    private String gradingJSON;

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
