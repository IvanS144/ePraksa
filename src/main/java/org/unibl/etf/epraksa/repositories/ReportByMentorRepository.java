package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.ReportByMentor;

public interface ReportByMentorRepository extends JpaRepository<ReportByMentor, Long> {
    @Query(value = "select rbm from ReportByMentor rbm " +
            "where exists (" +
            "select shi from StudentHasInternship shi " +
            "where shi.id.id = :id and shi.id.internshipId = :internshipId" +
            ")")
    ReportByMentor getReport(Long id, Long internshipId);
}
