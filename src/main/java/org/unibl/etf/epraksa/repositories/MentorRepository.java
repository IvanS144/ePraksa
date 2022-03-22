package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.epraksa.model.entities.Mentor;

import java.util.List;

public interface MentorRepository extends JpaRepository<Mentor,Long> {
    @Query("SELECT m FROM Mentor m WHERE" +
            "(m.company.Id = :companyId) AND" +
            "(m.isCurrentMentor=1)")
    List<Mentor> filter (@Param("companyId") Long companyId);
}
