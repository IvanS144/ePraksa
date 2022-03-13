package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.entities.WorkDairy;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntry;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntryPrevious;
import org.unibl.etf.epraksa.model.requests.WorkDiaryEntryRequest;
import org.unibl.etf.epraksa.repositories.WorkDiaryEntryPreviousRepository;
import org.unibl.etf.epraksa.repositories.WorkDiaryEntryRepository;
import org.unibl.etf.epraksa.repositories.WorkDiaryRepository;
import org.unibl.etf.epraksa.services.WorkDiaryService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@Transactional
public class WorkDiaryServiceImpl implements WorkDiaryService {
    private final WorkDiaryRepository workDiaryRepository;
    private final ModelMapper modelMapper;
    private final WorkDiaryEntryRepository workDiaryEntryRepository;
    private final WorkDiaryEntryPreviousRepository workDiaryEntryPreviousRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public WorkDiaryServiceImpl(WorkDiaryRepository workDiaryRepository, ModelMapper modelMapper, WorkDiaryEntryRepository workDiaryEntryRepository, WorkDiaryEntryPreviousRepository workDiaryEntryPreviousRepository) {
        this.workDiaryRepository = workDiaryRepository;
        this.modelMapper = modelMapper;
        this.workDiaryEntryRepository = workDiaryEntryRepository;
        this.workDiaryEntryPreviousRepository = workDiaryEntryPreviousRepository;
    }

    @Override
    public <T> T getWorkDiaryEntry(Long workDiaryId, Class<T> workDiaryClass) {
        WorkDairy workDairy = workDiaryRepository.findByWorkDairyId(workDiaryId);
        if(workDairy == null)
            workDairy = new WorkDairy();
        return modelMapper.map(workDairy, workDiaryClass);
    }

    @Override
    public <T> T insert(WorkDiaryEntryRequest request, Long id, Class<T> replyClass) {
//        setujem workDiaryId i entryId u reques-u
        request.setWorkDairyId(id);
        request.setEntryId(workDiaryEntryRepository.lastInsertEntryId(id) + 1);

//        kreiram novu previous tabelu za workDairyId = id i entryId = last entry;
        WorkDairyEntryPrevious workDairyEntryPrevious = new WorkDairyEntryPrevious();

//        zadnji previousId + 1
        Long previousID = workDiaryEntryPreviousRepository.lastInsertId()+1;

//        setujem entryId u previous tabeli
        workDairyEntryPrevious.setEntryId(previousID);

//        upisujem u previous tabelu
        workDairyEntryPrevious = workDiaryEntryPreviousRepository.saveAndFlush(workDairyEntryPrevious);
        entityManager.refresh(workDairyEntryPrevious);

//        pamtim previous tabelu u workDiaryEntry
        request.setPreviousId(previousID);

//        upisujem u workdiaryentry tabelu
        WorkDairyEntry workDairyEntry = modelMapper.map(request, WorkDairyEntry.class);
        workDairyEntry = workDiaryEntryRepository.saveAndFlush(workDairyEntry);
        entityManager.refresh(workDairyEntry);

        return modelMapper.map(workDairyEntry, replyClass);
    }

    @Override
    public void update(WorkDiaryEntryRequest request, Long workDiaryId, Long entryId) {
        if(workDiaryEntryRepository.existsByEntryIdAndWorkDairy_WorkDairyId(entryId,workDiaryId)){
//            podesim request entryId i workDiaryId
            request.setEntryId(entryId);
            request.setWorkDairyId(workDiaryId);

//            dohvatimo stari entry
            WorkDairyEntry previous = workDiaryEntryRepository.
                    findWorkDairyEntryByWorkDairy_WorkDairyIdAndEntryId(workDiaryId,entryId);

//            podesim request previousId
            request.setPreviousId(previous.getPreviousVersion().getEntryId());

//            request -> WorkDiaryEntry
            WorkDairyEntry workDairyEntry = modelMapper.map(request, WorkDairyEntry.class);


//            stari entry -> previousEntry
            WorkDairyEntryPrevious workDairyEntryPrevious = new WorkDairyEntryPrevious(previous);

//            updatujemo previousEntry
            workDiaryEntryPreviousRepository.saveAndFlush(workDairyEntryPrevious);

//            updatujemo novi Entry
            workDiaryEntryRepository.saveAndFlush(workDairyEntry);
        }
        else{
            throw new NotFoundException("Zapis za dati dnevnik rada ne postoji!");
        }
    }
}
