package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.model.dataTransferObjects.EntryDTO;
import org.unibl.etf.epraksa.model.entities.WorkDairy;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntry;
import org.unibl.etf.epraksa.repositories.WorkDiaryRepository;
import org.unibl.etf.epraksa.services.WorkDiaryService;

import javax.transaction.Transactional;

@Service
@Transactional
public class WorkDiaryServiceImpl implements WorkDiaryService {
    private final WorkDiaryRepository workDiaryRepository;
    private final ModelMapper modelMapper;

    public WorkDiaryServiceImpl(WorkDiaryRepository workDiaryRepository, ModelMapper modelMapper) {
        this.workDiaryRepository = workDiaryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public <T> T getWorkDiaryEntry(Long workDiaryId, Class<T> workDiaryClass) {
        WorkDairy workDairy = workDiaryRepository.findByWorkDairyId(workDiaryId);
        if(workDairy == null)
            workDairy = new WorkDairy();
        return modelMapper.map(workDairy, workDiaryClass);
    }
}
