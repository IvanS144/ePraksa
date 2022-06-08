package org.unibl.etf.epraksa.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class StudentHasInternshipPK implements Serializable {

    @Column(name="ID")
    private Long id;
    @Column(name="InternshipID")
    private Long internshipId;

    public StudentHasInternshipPK(Long id, Long internshipId) {
        this.id = id;
        this.internshipId = internshipId;
    }
}
