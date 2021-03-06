package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.WorkDairy;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntry;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntryPK;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntryPrevious;

import javax.swing.text.StyledEditorKit;
import java.util.Optional;

public interface WorkDiaryEntryRepository extends JpaRepository<WorkDairyEntry, WorkDairyEntryPK> {
//    Boolean existsByEntryIdAndWorkDairy_WorkDairyId(Long entryID, Long workDiaryEntryID);

    Boolean existsById_EntryIDAndId_WorkDairyID(Long entryID, Long workDiaryEntryID);

    @Query(nativeQuery = true,
            value = "SELECT MAX(wde.EntryID) FROM work_dairy_entry wde WHERE wde.WorkDairyID = :id")
    Long lastInsertEntryId(Long id);
//    @Query(value = "SELECT wde.previousVersion.entryId FROM WorkDairyEntry wde " +
//            "WHERE wde.entryId = :entryId AND wde.workDairy.workDairyId = :workEntryId")
//    Long getPreviousIdByEntryIdAndWorkDiaryEntryId(Long entryId, Long workEntryId);

//    Optional<WorkDairyEntry> findWorkDairyEntryByWorkDairy_WorkDairyIdAndEntryId(Long workDiaryID, Long entryId);

    Optional<WorkDairyEntry> findWorkDairyEntryById_WorkDairyIDAndId_EntryID(Long workDiaryID, Long entryId);
}
