package org.unibl.etf.epraksa.model.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.unibl.etf.epraksa.model.entities.json.ProjectEntryJSON;
import org.unibl.etf.epraksa.model.entities.json.WorkExperienceEntryJSON;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "cv")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
public class CV {

    @Id
    @Column(name = "ID",
            nullable = false)
    private Long id;

    @Basic
    @Column(name = "PortfolioLink",
            length = 45)
    private String porfolioLink;

    @Type(type="json")
    @Column(name = "Skills", columnDefinition = "json")
    private String[] skills;

    @Type(type="json")
    @Column(name = "Languages", columnDefinition="json")
    private String[] languages;

    @Type(type="json")
    @Column(name = "Hobbies", columnDefinition="json")
    private String[] hobbies;

    @Basic
    @Column(name = "Introduction")
    private String introduction;//polje oMeni

    @Type(type="json")
    @Column(name = "WorkExperience", columnDefinition="json")
    private List<WorkExperienceEntryJSON> workExperience;

    @Type(type="json")
    @Column(name="Projects", columnDefinition="json")
    private List<ProjectEntryJSON> projects;

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
