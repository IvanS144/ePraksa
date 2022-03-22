package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.WorkDairy;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntry;

import java.util.Optional;

public interface WorkDiaryRepository extends JpaRepository<WorkDairy,Long> {
    Optional<WorkDairy> findByWorkDairyId(Long id);
}
