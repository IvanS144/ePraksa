package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.dataTransferObjects.StudentDTO;

import java.util.List;

public interface StudentService {
    <T> List<T> getStudents(Class<T> replyClass);
    <T> T getStudent(Long studentId, Class<T> replyClass);
    <T> List<T> getCurrentPracticants(Class<T> replyClass);
}
