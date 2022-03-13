package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntryPrevious;

public interface WorkDiaryEntryPreviousRepository extends JpaRepository<WorkDairyEntryPrevious, Long> {
    @Query(nativeQuery = true,
    value = "SELECT MAX(EntryID) FROM work_dairy_entry_previous")
    Long lastInsertId();
}
