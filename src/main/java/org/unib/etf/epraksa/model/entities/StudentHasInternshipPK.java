package org.unib.etf.epraksa.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class StudentHasInternshipPK implements Serializable {

    private Long id;
    private Long internshipId;

    public StudentHasInternshipPK(Long id, Long internshipId) {
        this.id = id;
        this.internshipId = internshipId;
    }
}
