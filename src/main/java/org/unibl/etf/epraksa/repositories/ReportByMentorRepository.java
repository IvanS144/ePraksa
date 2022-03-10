package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.ReportByMentor;

public interface ReportByMentorRepository extends JpaRepository<ReportByMentor, Long> {
    @Query(value = "select shi.report " +
            "from StudentHasInternship shi " +
            "where shi.id.id = :id and shi.id.internshipId = :internshipId")
//    @Query(value = "select rbm.reportId, rbm.opinionJSON, rbm.createdAt, rbm.lastModifiedDate, rbm.deletedDate " +
//            "from ReportByMentor rbm " +
//            "inner join StudentHasInternship shi on shi.report.reportId = rbm.reportId " +
//            "where shi.id.id = :id and shi.id.internshipId = :internshipId")
    ReportByMentor getReport(Long id, Long internshipId);
}
