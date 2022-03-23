package org.unibl.etf.epraksa.services.impl;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.SQLGrammarException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.entities.*;
import org.unibl.etf.epraksa.model.requests.WorkDiaryEntryRequest;
import org.unibl.etf.epraksa.repositories.WorkDiaryEntryPreviousRepository;
import org.unibl.etf.epraksa.repositories.WorkDiaryEntryRepository;
import org.unibl.etf.epraksa.repositories.WorkDiaryRepository;
import org.unibl.etf.epraksa.services.WorkDiaryService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class WorkDiaryServiceImpl implements WorkDiaryService{
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
        WorkDairy workDairy = workDiaryRepository.findByWorkDairyId(workDiaryId)
                .orElseThrow(() -> new NotFoundException("Nije pronadjen dnevnik: " + workDiaryId));
        return modelMapper.map(workDairy, workDiaryClass);
    }

    @Override
    public <T> T insert(WorkDiaryEntryRequest request, Long id, Class<T> replyClass) {
//        Podesim work diary entry
        WorkDairyEntry workDairyEntry = modelMapper.map(request, WorkDairyEntry.class);
//        workDairyEntry.setCreatedAt(LocalDate.now());
//        workDairyEntry.setLastModifiedDate(LocalDate.now());
        WorkDairyEntryPK key = new WorkDairyEntryPK();
        key.setWorkDairyID(id);

        Long lastEntryID = workDiaryEntryRepository.lastInsertEntryId(id);
        if(lastEntryID==null) lastEntryID = 1L;
        else lastEntryID++;
        key.setEntryID(lastEntryID);

        workDairyEntry.setId(key);
        workDairyEntry.setWorkDairy(workDiaryRepository.findByWorkDairyId(id)
                .orElseThrow(()-> new NotFoundException("Nije pronadjen dnevnik: " + id)));

//        upisem isti
        workDairyEntry = workDiaryEntryRepository.saveAndFlush(workDairyEntry);
        entityManager.refresh(workDairyEntry);
//        entityManager.merge(workDairyEntry);
        return modelMapper.map(workDairyEntry, replyClass);
    }

    @Override
    public void update(WorkDiaryEntryRequest request, Long workDiaryId, Long entryId) {
//        if(workDiaryEntryRepository.existsByEntryIdAndWorkDairy_WorkDairyId(entryId,workDiaryId)){
        if(workDiaryEntryRepository.existsById_EntryIDAndId_WorkDairyID(entryId,workDiaryId)){

            WorkDairyEntryPK workDairyEntryPK = new WorkDairyEntryPK(workDiaryId, entryId);
//            WorkDiaryEntryPreviousPK workDiaryEntryPreviousPK = new WorkDiaryEntryPreviousPK(workDiaryId, entryId);

//            request -> WorkDiaryEntry / novi entry
            WorkDairyEntry newEntry = modelMapper.map(request, WorkDairyEntry.class);
            newEntry.setId(workDairyEntryPK);
//            newEntry.setLastModifiedDate(LocalDate.now());
            newEntry.setWorkDairy(workDiaryRepository.findByWorkDairyId(workDiaryId)
                    .orElseThrow(()-> new NotFoundException("Nije pronadjen dnevnik: " + workDiaryId)));

//            dohvatimo stari entry
            WorkDairyEntry oldEntry = workDiaryEntryRepository
                    .findById(workDairyEntryPK)
                    .orElseThrow(()-> new NotFoundException("Nije pronadjen odgovarajuci zapis: " +
                            entryId + " za dnevnik rada: " + workDiaryId + " !!!"));
            oldEntry.setPreviousVersion(null);

//            kopiram kada je kreiran stari, pa ga stavljam na novi
            newEntry.setCreatedAt(oldEntry.getCreatedAt());


//            Ako postoji previous od starog entry, onda izbrisemo, u suprotnom nista
            if (workDiaryEntryPreviousRepository.existsById(workDairyEntryPK))
            {
                workDiaryEntryPreviousRepository.deleteById(workDairyEntryPK);
            }

//            stari entry -> previousEntry
            WorkDairyEntryPrevious workDairyEntryPrevious = new WorkDairyEntryPrevious(oldEntry);

//            sacuvamo previousEntry
            workDiaryEntryPreviousRepository.saveAndFlush(workDairyEntryPrevious);

//            updatujemo novi Entry
            workDiaryEntryRepository.saveAndFlush(newEntry);
        }
        else{
            throw new NotFoundException("Zapis: " + entryId + " za dati dnevnik rada: " + workDiaryId + " ne postoji!");
        }
    }
}
