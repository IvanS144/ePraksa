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
    public <T> T getWorkDiary(Long workDiaryId, Class<T> workDiaryClass) {
        WorkDairy workDairy = workDiaryRepository.findById(workDiaryId)
                .orElseThrow(() -> new NotFoundException("Nije pronadjen dnevnik: " + workDiaryId));
        return modelMapper.map(workDairy, workDiaryClass);
    }

    @Override
    public <T> T getWorkDiary(Long studentId, Long internshipId, Class<T> workDiaryClass) {
        WorkDairy workDairy = workDiaryRepository.getWorkDairy(studentId, internshipId)
                .orElseThrow(() -> new NotFoundException("Nije pronadjen dnevnik!"));
        return modelMapper.map(workDairy, workDiaryClass);
    }

    @Override
    public <T> T insert(WorkDiaryEntryRequest request,Class<T> replyClass) {
//        Podesim work diary entry
        WorkDairyEntry workDairyEntry = modelMapper.map(request, WorkDairyEntry.class);
        WorkDairyEntryPK key = new WorkDairyEntryPK();
        key.setWorkDairyID(request.getWorkDairyId());

        Long lastEntryID = workDiaryEntryRepository.lastInsertEntryId(request.getWorkDairyId());
        if(lastEntryID==null) lastEntryID = 1L;
        else lastEntryID++;
        key.setEntryID(lastEntryID);

        workDairyEntry.setId(key);
        workDairyEntry.setDate(LocalDate.now());
        //workDairyEntry.setWorkDairy(workDiaryRepository.findById(id)
         //       .orElseThrow(()-> new NotFoundException("Nije pronadjen dnevnik: " + id)));

//        upisem isti
        workDairyEntry = workDiaryEntryRepository.saveAndFlush(workDairyEntry);
        entityManager.refresh(workDairyEntry);
//        entityManager.merge(workDairyEntry);
        return modelMapper.map(workDairyEntry, replyClass);
    }

    @Override
    public void update(WorkDiaryEntryRequest request, Long workDiaryId, Long entryId) {
        if(workDiaryEntryRepository.existsById_EntryIDAndId_WorkDairyID(entryId,workDiaryId)){

            WorkDairyEntryPK workDairyEntryPK = new WorkDairyEntryPK(workDiaryId, entryId);

//            request -> WorkDiaryEntry / novi entry
            WorkDairyEntry newEntry = modelMapper.map(request, WorkDairyEntry.class);
            newEntry.setId(workDairyEntryPK);
            //newEntry.setWorkDairy(workDiaryRepository.findById(workDiaryId)
             //       .orElseThrow(()-> new NotFoundException("Nije pronadjen dnevnik: " + workDiaryId)));

//            dohvatimo stari entry
            WorkDairyEntry oldEntry = workDiaryEntryRepository
                    .findById(workDairyEntryPK)
                    .orElseThrow(()-> new NotFoundException("Nije pronadjen odgovarajuci zapis: " +
                            entryId + " za dnevnik rada: " + workDiaryId + " !!!"));
            oldEntry.setPreviousVersion(null);

//            kopiram kada je kreiran stari, pa ga stavljam na novi
//            da se ne bi izgubio datum kada je prvi put kreiran entry
            newEntry.setCreatedAt(oldEntry.getCreatedAt());

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
