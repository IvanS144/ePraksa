package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.epraksa.model.entities.ReportByMentorQuestions;

public interface ReportByMentorQuestionsRepository extends JpaRepository<ReportByMentorQuestions,Integer>
{
}
