package org.unibl.etf.epraksa.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WorkDiaryEntryPreviousPK implements Serializable {
    @Column(name = "WorkDairyyID", nullable = false)
    private Long workDairyyID;

    @Column(name = "EntryyID", nullable = false)
    private Long entryyID;
}
