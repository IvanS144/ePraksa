package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.entities.State;
import org.unibl.etf.epraksa.model.requests.WorkDiaryEntryRequest;
import java.util.List;

public interface WorkDiaryService {
    <T> T getWorkDiary(Long workDiaryId, Class<T> workDiaryClass);

    <T> T getWorkDiary(Long studentId, Long internshipId, Class<T> workDiaryClass);
    <T> T insert(WorkDiaryEntryRequest request, Class<T> replyClass);
    public void update(WorkDiaryEntryRequest request, Long workDiaryid, Long entryId);

    void updateStateByStudentAndInternship(Long studentId, Long internshipId, State state);

    void updateStateByWorkDiary(Long workDiaryId, State state);

    <T> List<T> getWorkDiariesByStudent(Long studentId, Class<T> replyClass);
}
