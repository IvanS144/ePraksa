package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntryPK;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntryPrevious;
import org.unibl.etf.epraksa.model.entities.WorkDiaryEntryPreviousPK;

public interface WorkDiaryEntryPreviousRepository extends JpaRepository<WorkDairyEntryPrevious, WorkDairyEntryPK> {
}
