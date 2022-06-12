package org.unibl.etf.epraksa.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class WorkDiaryEntryPK implements Serializable {

    @Column(name = "WorkDiaryID", nullable = false)
    private Long workDiaryID;

    @Column(name = "EntryID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryID;

    public WorkDiaryEntryPK(Long workDiaryID, Long entryID) {
        this.workDiaryID = workDiaryID;
        this.entryID = entryID;
    }
}
