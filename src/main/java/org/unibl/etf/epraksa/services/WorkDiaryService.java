package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.requests.WorkDiaryEntryRequest;

public interface WorkDiaryService {
    <T> T getWorkDiaryEntry(Long workDiaryId, Class<T> workDiaryClass);
    <T> T insert(WorkDiaryEntryRequest request, Long id, Class<T> replyClass);
    public void update(WorkDiaryEntryRequest request, Long workDiaryid, Long entryId);
}
