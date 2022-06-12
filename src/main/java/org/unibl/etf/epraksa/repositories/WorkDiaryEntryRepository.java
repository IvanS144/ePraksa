package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.WorkDiaryEntry;
import org.unibl.etf.epraksa.model.entities.WorkDiaryEntryPK;

import java.util.Optional;

public interface WorkDiaryEntryRepository extends JpaRepository<WorkDiaryEntry, WorkDiaryEntryPK> {
//    Boolean existsByEntryIdAndWorkDairy_WorkDairyId(Long entryID, Long workDiaryEntryID);

    Boolean existsById_EntryIDAndId_WorkDairyID(Long entryID, Long workDiaryEntryID);

    @Query(nativeQuery = true,
            value = "SELECT MAX(wde.EntryID) FROM work_diary_entry wde WHERE wde.WorkDiaryID = :id")
    Long lastInsertEntryId(Long id);
//    @Query(value = "SELECT wde.previousVersion.entryId FROM WorkDairyEntry wde " +
//            "WHERE wde.entryId = :entryId AND wde.workDairy.workDairyId = :workEntryId")
//    Long getPreviousIdByEntryIdAndWorkDiaryEntryId(Long entryId, Long workEntryId);

//    Optional<WorkDairyEntry> findWorkDairyEntryByWorkDairy_WorkDairyIdAndEntryId(Long workDiaryID, Long entryId);

    Optional<WorkDiaryEntry> findWorkDairyEntryById_WorkDairyIDAndId_EntryID(Long workDiaryID, Long entryId);
}
