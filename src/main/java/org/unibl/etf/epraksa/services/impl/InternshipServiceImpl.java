package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.BadRequestException;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.dataTransferObjects.ReportByMentorDTO;
import org.unibl.etf.epraksa.model.entities.Internship;
import org.unibl.etf.epraksa.model.entities.InternshipType;
import org.unibl.etf.epraksa.model.entities.ReportByMentor;
import org.unibl.etf.epraksa.model.requests.InternshipRequest;
import org.unibl.etf.epraksa.repositories.InternshipRepository;
import org.unibl.etf.epraksa.repositories.ReportByMentorRepository;
import org.unibl.etf.epraksa.repositories.StudentHasInternshipRepository;
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

    public InternshipServiceImpl(InternshipRepository internshipRepository, StudentHasInternshipRepository studentHasInternshipRepository,
                                 ModelMapper modelMapper, ReportByMentorRepository reportByMentorRepository) {
        this.internshipRepository = internshipRepository;
        this.studentHasInternshipRepository = studentHasInternshipRepository;
        this.modelMapper = modelMapper;
        this.reportByMentorRepository = reportByMentorRepository;
    }

    @Override
    public void setAcceptanceStatus(Long internshipId, Boolean isAccepted) {
        if (!internshipRepository.existsById(internshipId)) {
            throw new NotFoundException("Ta praksa ne postoji");
        } else {
            Internship internship = internshipRepository.getById(internshipId);
            internship.setIsAccepted(isAccepted);
            internshipRepository.saveAndFlush(internship);
        }

    }

    @Override
    public <T> List<T> filter(Long id, String type, Boolean isPublished, Class<T> replyClass) {

        if(isPublished==null)
            isPublished=true;

        InternshipType it = null;

        if (type != null)
            it = InternshipType.valueOf(type);

        return internshipRepository.filter(id, it, isPublished)
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
        internship.setIsPublished(false);
        internship.setIsFinished(false);
        internship = internshipRepository.saveAndFlush(internship);
        entityManager.refresh(internship);
        return modelMapper.map(internship, replyClass);

    }

    @Override
    public void setFinishedStatus(Long internshipId, Boolean isFinished) {
        if (!internshipRepository.existsById(internshipId)) {
            throw new NotFoundException("Ta praksa ne postoji");
        } else {
            Internship internship = internshipRepository.getById(internshipId);
            internship.setIsFinished(isFinished);
            internshipRepository.saveAndFlush(internship);
        }
    }

    @Override
    public <T> T getReport(Long studentId, Long internshipId, Class<T> replyClass) {

        ReportByMentor reportByMentor = reportByMentorRepository.getReport(studentId, internshipId);

        if (reportByMentor == null)
            reportByMentor = new ReportByMentor();

        return modelMapper.map(reportByMentor, replyClass);
    }

    public <T> List<T> getAllStudentsOnInternship(Long internshipId, Class<T> replyClass) {
        return studentHasInternshipRepository.getAllStudentsOnInternship(internshipId).stream().map(s -> modelMapper.map(s, replyClass)).collect(Collectors.toList());
    }
}
