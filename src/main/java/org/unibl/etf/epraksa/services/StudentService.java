package org.unibl.etf.epraksa.services;

import java.util.List;

public interface StudentService {
    <T> List<T> getStudents(Long studentID, Boolean isPracticant, Class<T> replyClass);
}
