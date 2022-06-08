package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.Internship;
import org.unibl.etf.epraksa.model.entities.InternshipStatus;
import org.unibl.etf.epraksa.model.entities.InternshipType;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {

    @Query("SELECT i FROM Internship i WHERE" +
            "(:type IS NULL OR i.internshipType=:type) AND" +
            "(:status IS NULL OR i.status=:status) AND" +
            "(:mentorId IS NULL OR i.mentor.Id =:mentorId) AND"+
    "(:companyId IS NULL OR i.company.Id= :companyId)")
    List<Internship> filter (InternshipType type, Long mentorId, Long companyId, InternshipStatus status);
}
