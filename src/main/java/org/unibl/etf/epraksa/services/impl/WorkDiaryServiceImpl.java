package org.unibl.etf.epraksa.services.impl;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.SQLGrammarException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

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
        WorkDairy workDairy = workDiaryRepository.findByWorkDairyId(workDiaryId)
                .orElseThrow(() -> new NotFoundException("Nije pronadjen dnevnik: " + workDiaryId));
        return modelMapper.map(workDairy, workDiaryClass);
    }

    @Override
    public <T> T insert(WorkDiaryEntryRequest request, Long id, Class<T> replyClass) {
        System.out.println("Radi2");
        //        setujem workDiaryId i entryId u reques-u
        request.setWorkDairyId(id);
        if(workDiaryEntryRepository.lastInsertEntryId(id) == null){
            request.setEntryId(1L);
        }
        else{
            request.setEntryId(workDiaryEntryRepository.lastInsertEntryId(id) + 1);
        }
        System.out.println("Radi3");
//        Podesim work diary entry
        WorkDairyEntry workDairyEntry = modelMapper.map(request, WorkDairyEntry.class);
//        workDairyEntry.setWorkDairy(workDiaryRepository.findByWorkDairyId(id)
//                .orElseThrow(()-> new NotFoundException("Nije pronadjen dnevnik: " + id)));
        workDairyEntry.setCreatedAt(LocalDate.now());
        workDairyEntry.setLastModifiedDate(LocalDate.now());
        System.out.println("Radi4");
//        upisem isti
        try {
            workDairyEntry = workDiaryEntryRepository.saveAndFlush(workDairyEntry);
        }catch (InvalidDataAccessResourceUsageException e){
            System.out.println("PROSOOOOOOO");

            System.out.println(e.getRootCause().getMessage());
            System.out.println(e.getMostSpecificCause().getMessage());
        }
//        workDairyEntry = workDiaryEntryRepository.saveAndFlush(workDairyEntry);
        System.out.println("Radi5");
        entityManager.refresh(workDairyEntry);
        System.out.println("Radi6");
        return modelMapper.map(workDairyEntry, replyClass);
    }

    @Override
    public void update(WorkDiaryEntryRequest request, Long workDiaryId, Long entryId) {
//        if(workDiaryEntryRepository.existsByEntryIdAndWorkDairy_WorkDairyId(entryId,workDiaryId)){
        if(workDiaryEntryRepository.existsById_EntryIDAndId_WorkDairyID(entryId,workDiaryId)){
//            podesim request entryId i workDiaryId
            request.setEntryId(entryId);
            request.setWorkDairyId(workDiaryId);

//            request -> WorkDiaryEntry / novi entry
            WorkDairyEntry workDairyEntry = modelMapper.map(request, WorkDairyEntry.class);

//            dohvatimo stari entry
            WorkDairyEntry oldEntry = workDiaryEntryRepository.
//                    findWorkDairyEntryByWorkDairy_WorkDairyIdAndEntryId(workDiaryId,entryId)
                    findWorkDairyEntryById_WorkDairyIDAndId_EntryID(workDiaryId,entryId)
                    .orElseThrow(()-> new NotFoundException("Nije pronadjen odgovarajuci zapis: " +
                            entryId + " za dnevnik rada: " + workDiaryId + " !!!"));

//            izbrisemo previous od starog entry-a
            workDiaryEntryPreviousRepository.deleteById(oldEntry.getPreviousVersion().getEntryId());

//            stari entry -> previousEntry
            WorkDairyEntryPrevious workDairyEntryPrevious = new WorkDairyEntryPrevious(oldEntry);

//            sacuvamo previousEntry
            workDairyEntryPrevious = workDiaryEntryPreviousRepository.saveAndFlush(workDairyEntryPrevious);

//            osvjezim vec sacuvan previousEntry kako bih pribavio njegov id
            entityManager.refresh(workDairyEntryPrevious);

//            postavim previous u novi entry
            workDairyEntry.setPreviousVersion(workDairyEntryPrevious);

//            updatujemo novi Entry
            workDiaryEntryRepository.saveAndFlush(workDairyEntry);
        }
        else{
            throw new NotFoundException("Zapis: " + entryId + " za dati dnevnik rada: " + workDiaryId + " ne postoji!");
        }
    }
}
