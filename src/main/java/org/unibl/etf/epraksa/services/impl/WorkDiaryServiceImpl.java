package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.entities.*;
import org.unibl.etf.epraksa.model.requests.WorkDiaryEntryRequest;
import org.unibl.etf.epraksa.repositories.StudentRepository;
import org.unibl.etf.epraksa.repositories.WorkDiaryEntryPreviousRepository;
import org.unibl.etf.epraksa.repositories.WorkDiaryEntryRepository;
import org.unibl.etf.epraksa.repositories.WorkDiaryRepository;
import org.unibl.etf.epraksa.services.WorkDiaryService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class WorkDiaryServiceImpl implements WorkDiaryService{
    private final WorkDiaryRepository workDiaryRepository;
    private final ModelMapper modelMapper;
    private final WorkDiaryEntryRepository workDiaryEntryRepository;
    private final WorkDiaryEntryPreviousRepository workDiaryEntryPreviousRepository;

    private final StudentRepository studentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public WorkDiaryServiceImpl(WorkDiaryRepository workDiaryRepository, ModelMapper modelMapper, WorkDiaryEntryRepository workDiaryEntryRepository, WorkDiaryEntryPreviousRepository workDiaryEntryPreviousRepository, StudentRepository studentRepository) {
        this.workDiaryRepository = workDiaryRepository;
        this.modelMapper = modelMapper;
        this.workDiaryEntryRepository = workDiaryEntryRepository;
        this.workDiaryEntryPreviousRepository = workDiaryEntryPreviousRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public <T> T getWorkDiary(Long workDiaryId, Class<T> workDiaryClass) {
        WorkDiary workDiary = workDiaryRepository.findById(workDiaryId)
                .orElseThrow(() -> new NotFoundException("Nije pronadjen dnevnik: " + workDiaryId));
        return modelMapper.map(workDiary, workDiaryClass);
    }

    @Override
    public <T> T getWorkDiary(Long studentId, Long internshipId, Class<T> workDiaryClass) {
        WorkDiary workDiary = workDiaryRepository.getWorkDiary(studentId, internshipId)
                .orElseThrow(() -> new NotFoundException("Nije pronadjen dnevnik!"));
        return modelMapper.map(workDiary, workDiaryClass);
    }

    @Override
    public <T> T insert(WorkDiaryEntryRequest request,Class<T> replyClass) {
//        Podesim work diary entry
        WorkDiaryEntry workDiaryEntry = modelMapper.map(request, WorkDiaryEntry.class);
        WorkDiaryEntryPK key = new WorkDiaryEntryPK();
        key.setWorkDiaryID(request.getWorkDiaryId());

        Long lastEntryID = workDiaryEntryRepository.lastInsertEntryId(request.getWorkDiaryId());
        if(lastEntryID==null) lastEntryID = 1L;
        else lastEntryID++;
        key.setEntryID(lastEntryID);

        workDiaryEntry.setId(key);
        workDiaryEntry.setDate(LocalDate.now());
        //workDairyEntry.setWorkDairy(workDiaryRepository.findById(id)
         //       .orElseThrow(()-> new NotFoundException("Nije pronadjen dnevnik: " + id)));

//        upisem isti
        workDiaryEntry = workDiaryEntryRepository.saveAndFlush(workDiaryEntry);
        entityManager.refresh(workDiaryEntry);
//        entityManager.merge(workDairyEntry);
        return modelMapper.map(workDiaryEntry, replyClass);
    }

    @Override
    public void update(WorkDiaryEntryRequest request, Long workDiaryId, Long entryId) {
        request.setWorkDiaryId(workDiaryId);//TODO bolje smisliti
        if(workDiaryEntryRepository.existsById_EntryIDAndId_WorkDiaryID(entryId,workDiaryId)){

            WorkDiaryEntryPK workDiaryEntryPK = new WorkDiaryEntryPK(workDiaryId, entryId);

//            request -> WorkDiaryEntry / novi entry
            WorkDiaryEntry newEntry = modelMapper.map(request, WorkDiaryEntry.class);
            newEntry.setId(workDiaryEntryPK);
            //newEntry.setWorkDairy(workDiaryRepository.findById(workDiaryId)
             //       .orElseThrow(()-> new NotFoundException("Nije pronadjen dnevnik: " + workDiaryId)));

//            dohvatimo stari entry
            WorkDiaryEntry oldEntry = workDiaryEntryRepository
                    .findById(workDiaryEntryPK)
                    .orElseThrow(()-> new NotFoundException("Nije pronadjen odgovarajuci zapis: " +
                            entryId + " za dnevnik rada: " + workDiaryId + " !!!"));
            oldEntry.setPreviousVersion(null);

//            kopiram kada je kreiran stari, pa ga stavljam na novi
//            da se ne bi izgubio datum kada je prvi put kreiran entry
            newEntry.setCreatedAt(oldEntry.getCreatedAt());
            newEntry.setDate(newEntry.getCreatedAt());

//            stari entry -> previousEntry
            WorkDiaryEntryPrevious workDiaryEntryPrevious = new WorkDiaryEntryPrevious(oldEntry);

//            sacuvamo previousEntry
            workDiaryEntryPreviousRepository.saveAndFlush(workDiaryEntryPrevious);

//            updatujemo novi Entry
            workDiaryEntryRepository.saveAndFlush(newEntry);
        }
        else{
            throw new NotFoundException("Zapis: " + entryId + " za dati dnevnik rada: " + workDiaryId + " ne postoji!");
        }
    }

    @Override
    public void updateStateByStudentAndInternship(Long studentId, Long internshipId, State state) {
        WorkDiary workDiary = workDiaryRepository.getWorkDiary(studentId, internshipId)
                .orElseThrow(() -> new NotFoundException("Nije pronadjen dnevnik!"));

        workDiary.setState(state);

        workDiaryRepository.saveAndFlush(workDiary);
    }

    @Override
    public void updateStateByWorkDiary(Long workDiaryId, State state) {
        WorkDiary workDiary = workDiaryRepository.findById(workDiaryId)
                .orElseThrow(() -> new NotFoundException("Nije pronadjen dnevnik!"));

        workDiary.setState(state);

        workDiaryRepository.saveAndFlush(workDiary);
    }

    @Override
    public <T> T getWorkDiaryByStudent(Long studentId, Class<T> replyClass) {
        if(studentRepository.existsById(studentId))
        {
            List<WorkDiary> list = workDiaryRepository.getWorkDiariesForStudent(studentId);
            if(list.isEmpty()){
                throw new NotFoundException("Student još nema dnevnik rada");
            }
            return modelMapper.map(list.get(0), replyClass);
            /*return workDiaryRepository.getWorkDairiesForStudent(studentId).stream().map(e -> modelMapper.map(e,replyClass)).collect(Collectors.toList());*/
        }
        else
        {
            throw new NotFoundException("Trazeni student ne postoji");
        }
    }
}
