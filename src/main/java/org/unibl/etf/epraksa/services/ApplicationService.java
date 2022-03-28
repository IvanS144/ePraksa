package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.entities.Comment;
import org.unibl.etf.epraksa.model.entities.State;
import org.unibl.etf.epraksa.model.requests.ApplicationRequest;

import java.util.List;

public interface ApplicationService {
    <T> List<T> getApplicationsForInternship(Long internshipId, State state, Class<T> replyClass);
    <T> List<T> getApplicationsForStudent(Long studentId, State state, Class<T> replyClass);
    <T> T getApplication(Long internshipId, Long studentId, Class<T> replyClass);
    void insert(ApplicationRequest request);
    void setState(Long internshipId, Long studentId, State state, Comment comment);
}
