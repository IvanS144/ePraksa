package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.entities.*;
import org.unibl.etf.epraksa.model.requests.ApplicationRequest;
import org.unibl.etf.epraksa.repositories.*;
import org.unibl.etf.epraksa.services.ApplicationService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
    public void insert(ApplicationRequest request) {
        Application application = modelMapper.map(request, Application.class);
        application.setState(State.PENDING);
        applicationRepository.saveAndFlush(application);

    }

    @Override
    public void setState(Long internshipId, Long studentId, State state) {
        ApplicationPK pk = new ApplicationPK(internshipId, studentId);
        Application application = applicationRepository.findById(pk).orElseThrow(()-> new NotFoundException("Ta prijava ne postoji"));
        application.setState(state);
        if(state.equals(State.ACCEPTED))
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
        }
    }
}
