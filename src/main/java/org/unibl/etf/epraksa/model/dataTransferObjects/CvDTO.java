package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.unibl.etf.epraksa.model.entities.json.ProjectEntryJSON;
import org.unibl.etf.epraksa.model.entities.json.WorkExperienceEntryJSON;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.List;

@Data
public class CvDTO {
    private String porfolioLink;

    private String[] skills;

    private String[] languages;

    private String[] hobbies;

    private String introduction;//polje oMeni

    private List<WorkExperienceEntryJSON> workExperience;

    private List<ProjectEntryJSON> projects;
}
