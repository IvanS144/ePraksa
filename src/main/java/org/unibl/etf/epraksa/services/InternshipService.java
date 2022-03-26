package org.unibl.etf.epraksa.services;

import java.util.List;

import org.unibl.etf.epraksa.model.dataTransferObjects.InternshipDTO;
import org.unibl.etf.epraksa.model.entities.ReportByMentor;
import org.unibl.etf.epraksa.model.requests.InternshipRequest;

public interface InternshipService {

    void setAcceptanceStatus(Long internshipId, Boolean isAccepted);
    <T> List<T> filter(String type, Boolean isPublished, Long mentorId, Class<T> replyClass);
    <T> T insert(InternshipRequest request, Class<T> replyClass);
    <T> T setFinishedStatus(Long internshipId, Boolean isFinished, Class<T> replyClass);
    <T> T getReport(Long studentId, Long internshipId, Class<T> replyClass);
    <T> List<T> getAllStudentsOnInternship(Long internshipId, Class<T> replyClass);
    <T> T getInternship(Long internshipId, Class<T> replyCLass);
    <T> T setActive(Long internshipId, Class<T> replyClass);
//    <T> List<T> getInternshipsByMentor(Long mentorId, Class<T> replyClass);
}
