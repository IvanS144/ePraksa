package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.dataTransferObjects.ReportByMentorDTO;
import org.unibl.etf.epraksa.model.entities.Internship;
import org.unibl.etf.epraksa.model.entities.ReportByMentor;
import org.unibl.etf.epraksa.model.entities.ReportByMentorQuestions;
import java.util.List;

public interface ReportByMentorService
{
    <T> T getReport(Long studentId, Long internshipId, Class<T> replyClass);
    <T> T getLatestReport(Long studentId, Class<T> replyClass);
    <T> T updateReportFromMentor(Long internshipId, Long studentId, ReportByMentorDTO request, Class<T> replyClass);
    void deleteReportFromMentor(Long internshipId, Long studentId);
    List<ReportByMentorQuestions> getAllQuestions();
    void setReportInitialValues(Internship internship, ReportByMentor report);
}
