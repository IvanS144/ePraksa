package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.BadRequestException;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.entities.*;
import org.unibl.etf.epraksa.model.requests.InternshipRequest;
import org.unibl.etf.epraksa.repositories.*;
import org.unibl.etf.epraksa.services.InternshipService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final StudentHasInternshipRepository studentHasInternshipRepository;
    private final ModelMapper modelMapper;
    private final ReportByMentorRepository reportByMentorRepository;

    @PersistenceContext
    private EntityManager entityManager;
    private final WorkDiaryRepository workDiaryRepository;

    public InternshipServiceImpl(InternshipRepository internshipRepository, StudentHasInternshipRepository studentHasInternshipRepository,
                                 ModelMapper modelMapper, ReportByMentorRepository reportByMentorRepository,EntityManager entityManager, WorkDiaryRepository workDiaryRepository) {
        this.internshipRepository = internshipRepository;
        this.studentHasInternshipRepository = studentHasInternshipRepository;
        this.modelMapper = modelMapper;
        this.reportByMentorRepository = reportByMentorRepository;
        this.entityManager = entityManager;
        this.workDiaryRepository = workDiaryRepository;
    }

    @Override
    public void setAcceptanceStatus(Long internshipId, Boolean isAccepted) {
        if (!internshipRepository.existsById(internshipId)) {
            throw new NotFoundException("Ta praksa ne postoji");
        } else {
            Internship internship = internshipRepository.getById(internshipId);
            internship.setIsAccepted(isAccepted);
            internship.setIsPublished(true);

            internshipRepository.saveAndFlush(internship);
        }

    }

    @Override
    public <T> List<T> filter(String type, Boolean isPublished, Long mentorId, Boolean isAccepted, Long companyId, Class<T> replyClass) {

        if(isPublished==null && isAccepted==null)
            isPublished=true;

        InternshipType it = null;

        if (type != null)
            it = InternshipType.valueOf(type);

        return internshipRepository.filter(it, isPublished, mentorId, isAccepted, companyId)
                .stream()
                .map(e -> modelMapper.map(e, replyClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> T insert(InternshipRequest request, Class<T> replyClass) {
        Internship internship = modelMapper.map(request, Internship.class);
        if (internship.getSubmissionDue().isAfter(internship.getStartDate()) || internship.getEndDate().isBefore(internship.getStartDate()) || internship.getStartDate().isEqual(internship.getEndDate()))
            throw new BadRequestException("Datumi nisu validni");
        internship.setInternshipId(null);
        if(InternshipType.STRUCNA.equals(internship.getInternshipType()))
        {
            internship.setIsPublished(false);
        }
        else
        {
            internship.setIsPublished(true);internship.setIsAccepted(true);
        }
        internship.setIsActive(false);
        internship.setIsFinished(false);
        internship = internshipRepository.saveAndFlush(internship);
        entityManager.refresh(internship);
        return modelMapper.map(internship, replyClass);

    }

    @Override
    public <T> T setFinishedStatus(Long internshipId, Boolean isFinished, Class<T> replyClass) {
        if (!internshipRepository.existsById(internshipId)) {
            throw new NotFoundException("Ta praksa ne postoji");
        } else {
            Internship internship = internshipRepository.getById(internshipId);
            internship.setIsFinished(isFinished);
            internship.setIsActive(isFinished? false : true);
            internship=internshipRepository.saveAndFlush(internship);
            entityManager.refresh(internship);
            return modelMapper.map(internship, replyClass);
        }
    }

    @Override
    public <T> T setActive(Long internshipId, Class<T> replyClass) {
        if (!internshipRepository.existsById(internshipId)) {
            throw new NotFoundException("Ta praksa ne postoji");
        } else {
            Internship internship = internshipRepository.getById(internshipId);
            internship.setIsActive(true);
            internship.setIsPublished(false);
            internship=internshipRepository.saveAndFlush(internship);
            entityManager.refresh(internship);
            for(var a : internship.getApplications()){
                WorkDairy workDairy = new WorkDairy();
                workDairy=workDiaryRepository.saveAndFlush(workDairy);
                entityManager.refresh(workDairy);
                StudentHasInternshipPK pks = new StudentHasInternshipPK(a.getId().getStudentId(), internshipId);
                StudentHasInternship shi = new StudentHasInternship();
                shi.setId(pks);
                shi.setInternship(internship);
                shi.setStudent(a.getStudent());
                shi.setWorkDairy(workDairy);
                studentHasInternshipRepository.saveAndFlush(shi);
            }
            return modelMapper.map(internship, replyClass);
        }
    }

    @Override
    public <T> T getReport(Long studentId, Long internshipId, Class<T> replyClass) {
        ReportByMentor reportByMentor = reportByMentorRepository
                .getReport(studentId, internshipId)
                .orElseThrow(()-> new NotFoundException("Nije pronadjen izvjestaj za studenta: " + studentId
                + "na praksi: "+internshipId));

        return modelMapper.map(reportByMentor, replyClass);
    }

    public <T> List<T> getAllStudentsOnInternship(Long internshipId, Class<T> replyClass) {
        return studentHasInternshipRepository.getAllStudentsOnInternship(internshipId).stream().map(s -> modelMapper.map(s, replyClass)).collect(Collectors.toList());
    }

    @Override
    public <T> T getInternship(Long internshipId, Class<T> replyCLass) {
        return modelMapper.map(internshipRepository
                .findById(internshipId)
                .orElseThrow(()-> new NotFoundException("Nije pronadjena praksa: " + internshipId)), replyCLass);
    }

//    @Override
//    public <T> List<T> getInternshipsByMentor(Long mentorId, Class<T> replyClass) {
//        return internshipRepository.getInternshipsByMentor(mentorId)
//                .stream()
//                .map(e-> modelMapper.map(e, replyClass))
//                .collect(Collectors.toList());
//    }
}
