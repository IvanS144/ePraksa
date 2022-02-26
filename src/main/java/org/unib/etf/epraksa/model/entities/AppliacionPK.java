package org.unib.etf.epraksa.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AppliacionPK implements Serializable {

    private Long internshipId;
    private Long studentId;

    public AppliacionPK(Long internshipId, Long studentId) {
        this.internshipId = internshipId;
        this.studentId = studentId;
    }
}
