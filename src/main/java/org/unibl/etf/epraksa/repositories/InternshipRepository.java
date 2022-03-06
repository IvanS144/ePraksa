package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.Internship;
import org.unibl.etf.epraksa.model.entities.InternshipType;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {

    @Query("SELECT i FROM Internship i WHERE" +
            "(:id IS NULL OR i.internshipId=:id) AND" +
            "(:type IS NULL OR i.internshipType=:type) AND" +
            "(:isPublished IS NULL OR i.isPublished=:isPublished)")
    List<Internship> filter (Long id, InternshipType type, Boolean isPublished);
}
