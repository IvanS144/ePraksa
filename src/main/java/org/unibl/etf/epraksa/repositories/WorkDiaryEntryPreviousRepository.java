package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.epraksa.model.entities.WorkDiaryEntryPK;
import org.unibl.etf.epraksa.model.entities.WorkDiaryEntryPrevious;

public interface WorkDiaryEntryPreviousRepository extends JpaRepository<WorkDiaryEntryPrevious, WorkDiaryEntryPK> {
}
