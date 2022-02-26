package org.unib.etf.epraksa.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class AppliacionPK implements Serializable {

    @Column(name="InternshipId")
    private Long internshipId;
    @Column(name="StudentId")
    private Long studentId;

    public AppliacionPK(Long internshipId, Long studentId) {
        this.internshipId = internshipId;
        this.studentId = studentId;
    }
}
