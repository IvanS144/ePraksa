package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.WorkDiary;

import java.util.List;
import java.util.Optional;

public interface WorkDiaryRepository extends JpaRepository<WorkDiary,Long> {

    //inicijalizujemo i vezani objekat studentOnInternship
    //@Query("SELECT wd from WorkDairy wd INNER JOIN FETCH wd.studentOnInternship WHERE wd.workDairyId=:workDairyId")
    //Optional<WorkDairy> findByIdAndIncludeInternship(@Param("workDairyId") Long workDairyId);
    @Query("SELECT shi.workDiary from StudentHasInternship shi WHERE shi.id.id = :studentId AND shi.id.internshipId = :internshipId")
    public Optional<WorkDiary> getWorkDiary(Long studentId, Long internshipId);

    @Query("SELECT shi.workDiary from StudentHasInternship shi WHERE shi.id.id = :studentId AND shi.workDiary.state=org.unibl.etf.epraksa.model.entities.State.PENDING")
    public List<WorkDiary> getWorkDiariesForStudent(Long studentId);
}
