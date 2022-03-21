package org.unibl.etf.epraksa.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDiaryEntryPreviousPK implements Serializable {
    @Column(name = "WorkDairyID", nullable = false)
    private Long workDairyID;

    @Column(name = "EntryID", nullable = false)
    private Long entryID;
}
