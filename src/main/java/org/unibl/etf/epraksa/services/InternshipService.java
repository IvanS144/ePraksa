package org.unibl.etf.epraksa.services;

import java.util.List;

import org.unibl.etf.epraksa.model.entities.Comment;
import org.unibl.etf.epraksa.model.entities.InternshipStatus;
import org.unibl.etf.epraksa.model.requests.InternshipRequest;

public interface InternshipService {
    void setAcceptanceStatus(Long internshipId, Boolean flag, Comment reason);
    <T> List<T> filter(String type, Long mentorId, Long companyId, InternshipStatus status, Class<T> replyClass);
    <T> T insert(InternshipRequest request, Class<T> replyClass);
    <T> T update(Long internshipId, InternshipRequest request, Class<T> replyClass);
    <T> T setFinishedStatus(Long internshipId, Class<T> replyClass);
    <T> List<T> getAllStudentsOnInternship(Long internshipId, Class<T> replyClass);
    <T> T getInternship(Long internshipId, Class<T> replyCLass);
    <T> T setActive(Long internshipId, Class<T> replyClass);
}
