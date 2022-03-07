package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.dataTransferObjects.EntryDTO;

public interface WorkDiaryService {
    <T> T getWorkDiaryEntry(Long workDiaryId, Class<T> workDiaryClass);
}
