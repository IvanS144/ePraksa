package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.epraksa.model.entities.WorkDairy;

import java.util.List;
import java.util.Optional;

public interface WorkDiaryRepository extends JpaRepository<WorkDairy,Long> {

    //inicijalizujemo i vezani objekat studentOnInternship
    //@Query("SELECT wd from WorkDairy wd INNER JOIN FETCH wd.studentOnInternship WHERE wd.workDairyId=:workDairyId")
    //Optional<WorkDairy> findByIdAndIncludeInternship(@Param("workDairyId") Long workDairyId);
    @Query("SELECT shi.workDairy from StudentHasInternship shi WHERE shi.id.id = :studentId AND shi.id.internshipId = :internshipId")
    public Optional<WorkDairy> getWorkDairy(Long studentId, Long internshipId);

    @Query("SELECT shi.workDairy from StudentHasInternship shi WHERE shi.id.id = :studentId ORDER BY shi.workDairy.createdAt DESC")
    public List<WorkDairy> getWorkDairiesForStudent(Long studentId);
}
