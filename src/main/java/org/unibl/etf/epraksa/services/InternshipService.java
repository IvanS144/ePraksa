package org.unibl.etf.epraksa.services;

import java.util.List;

import org.unibl.etf.epraksa.model.dataTransferObjects.InternshipDTO;
import org.unibl.etf.epraksa.model.entities.ReportByMentor;
import org.unibl.etf.epraksa.model.requests.InternshipRequest;

public interface InternshipService {

    public void setAcceptanceStatus(Long internshipId, Boolean isAccepted);
    <T> List<T> filter(Long id, String type, Boolean isPublished, Long mentorId, Class<T> replyClass);
    <T> T insert(InternshipRequest request, Class<T> replyClass);
    void setFinishedStatus(Long internshipId, Boolean isFinished);
    <T> T getReport(Long studentId, Long internshipId, Class<T> replyClass);
    <T> List<T> getAllStudentsOnInternship(Long internshipId, Class<T> replyClass);
//    <T> List<T> getInternshipsByMentor(Long mentorId, Class<T> replyClass);
}
