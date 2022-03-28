package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.Internship;
import org.unibl.etf.epraksa.model.entities.InternshipType;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {

    @Query("SELECT i FROM Internship i WHERE" +
            "(:type IS NULL OR i.internshipType=:type) AND" +
            "(:isPublished IS NULL OR i.isPublished=:isPublished) AND" +
            "(:mentorId IS NULL OR i.mentor.Id =:mentorId) AND"+
    "(:isAccepted IS NULL OR i.isAccepted= :isAccepted) AND"+
    "(:companyId IS NULL OR i.company.Id= :companyId)")
    List<Internship> filter (InternshipType type, Boolean isPublished, Long mentorId, Boolean isAccepted, Long companyId);
}
