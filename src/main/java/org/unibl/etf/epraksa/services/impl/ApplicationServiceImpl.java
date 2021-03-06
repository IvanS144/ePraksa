package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.ForbiddenException;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.entities.*;
import org.unibl.etf.epraksa.model.requests.ApplicationRequest;
import org.unibl.etf.epraksa.repositories.*;
import org.unibl.etf.epraksa.services.ApplicationService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentHasInternshipRepository studentHasInternshipRepository;
    private final WorkDiaryRepository workDiaryRepository;
    private final InternshipRepository internshipRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    @PersistenceContext
    private final EntityManager entityManager;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, StudentHasInternshipRepository studentHasInternshipRepository, WorkDiaryRepository workDiaryRepository, InternshipRepository internshipRepository, StudentRepository studentRepository, ModelMapper modelMapper, EntityManager entityManager) {
        this.applicationRepository = applicationRepository;
        this.studentHasInternshipRepository = studentHasInternshipRepository;
        this.workDiaryRepository = workDiaryRepository;
        this.internshipRepository = internshipRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }

    @Override
    public <T> List<T> getApplicationsForInternship(Long internshipId, State state, Class<T> replyClass) {
        return applicationRepository.filterByInternship(internshipId, state)
                .stream()
                .map(application -> modelMapper.map(application, replyClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> List<T> getApplicationsForStudent(Long studentId, State state, Class<T> replyClass) {
        return applicationRepository.getApplicationsByStudent(studentId, state)
                .stream()
                .map(application -> modelMapper.map(application,replyClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> T getApplication(Long internshipId, Long studentId, Class<T> replyClass) {
        ApplicationPK pk = new ApplicationPK(internshipId, studentId);
        return modelMapper.map(applicationRepository.findById(pk)
                            .orElseThrow(()-> new NotFoundException("Ta prijava ne postoji")), replyClass);
    }

    @Override
    public void insert(ApplicationRequest request) {
        Application application = modelMapper.map(request, Application.class);
        if(applicationRepository.existsById(application.getId())) {
            if(!applicationRepository.getById(application.getId()).getState().equals(State.DENIED))
            throw new ForbiddenException("Ve?? ste prijavljeni na ovu praksu");
        }
        application.setState(State.PENDING);
        application.setCreatedAt(LocalDate.now());
        applicationRepository.saveAndFlush(application);

    }

    @Override
    public void setState(Long internshipId, Long studentId, State state, Comment comment) {
        ApplicationPK pk = new ApplicationPK(internshipId, studentId);
        Application application = applicationRepository.findById(pk).orElseThrow(()-> new NotFoundException("Ta prijava ne postoji"));
        application.setState(state);
        /*if(state.equals(State.ACCEPTED))
        {
            WorkDairy workDairy = new WorkDairy();
            workDairy=workDiaryRepository.saveAndFlush(workDairy);
            entityManager.refresh(workDairy);
            StudentHasInternshipPK pks = new StudentHasInternshipPK(internshipId, studentId);
            StudentHasInternship shi = new StudentHasInternship();
            shi.setId(pks);
            shi.setInternship(internshipRepository.getById(internshipId));
            shi.setStudent(studentRepository.getById(studentId));
            shi.setWorkDairy(workDairy);
            studentHasInternshipRepository.saveAndFlush(shi);
        }*/
        if(state.equals(State.DENIED))
        {
            application.setReport(comment.getComment());
        }
        applicationRepository.saveAndFlush(application);
    }

    @Override
    public void delete(Long internshipId, Long studentId)
    {
        ApplicationPK pk = new ApplicationPK(internshipId, studentId);
        Application application = applicationRepository.findById(pk).orElseThrow(()-> new NotFoundException("Poku??avate da obri??ete nepostoje??u praksu"));
        Internship internship = internshipRepository.getById(internshipId);
        if(!internship.getStatus().equals(InternshipStatus.ACTIVE)) {
            application.setDeletedDate(LocalDate.now());
            applicationRepository.saveAndFlush(application);
        }
    }
}
