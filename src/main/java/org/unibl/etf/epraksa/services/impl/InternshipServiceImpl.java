package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.BadRequestException;
import org.unibl.etf.epraksa.exceptions.ForbiddenException;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.dataTransferObjects.ReportByMentorDTO;
import org.unibl.etf.epraksa.model.entities.*;
import org.unibl.etf.epraksa.model.entities.json.AnswerToTheQuestionnaireENUM;
import org.unibl.etf.epraksa.model.entities.json.OneEntryForQuestionnaireJSON;
import org.unibl.etf.epraksa.model.entities.json.OpinionByMentorJSON;
import org.unibl.etf.epraksa.model.entities.json.StudentQuestionnaireJSON;
import org.unibl.etf.epraksa.model.requests.InternshipRequest;
import org.unibl.etf.epraksa.repositories.*;
import org.unibl.etf.epraksa.services.InternshipService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final StudentHasInternshipRepository studentHasInternshipRepository;
    private final ModelMapper modelMapper;
    private final ReportByMentorRepository reportByMentorRepository;
    private final ReportByMentorQuestionsRepository reportByMentorQuestionsRepository;

    @PersistenceContext
    private EntityManager entityManager;
    private final WorkDiaryRepository workDiaryRepository;

    public InternshipServiceImpl(InternshipRepository internshipRepository, StudentHasInternshipRepository studentHasInternshipRepository,
                                 ModelMapper modelMapper, ReportByMentorRepository reportByMentorRepository, ReportByMentorQuestionsRepository reportByMentorQuestionsRepository, EntityManager entityManager, WorkDiaryRepository workDiaryRepository) {
        this.internshipRepository = internshipRepository;
        this.studentHasInternshipRepository = studentHasInternshipRepository;
        this.modelMapper = modelMapper;
        this.reportByMentorRepository = reportByMentorRepository;
        this.reportByMentorQuestionsRepository = reportByMentorQuestionsRepository;
        this.entityManager = entityManager;
        this.workDiaryRepository = workDiaryRepository;
    }

    @Override
    public void setAcceptanceStatus(Long internshipId, Boolean flag) {
        if (!internshipRepository.existsById(internshipId)) {
            throw new NotFoundException("Ta praksa ne postoji");
        } else {
            Internship internship = internshipRepository.getById(internshipId);
            internship.setStatus(flag? InternshipStatus.PUBLISHED : InternshipStatus.DENIED);

            internshipRepository.saveAndFlush(internship);
        }

    }

    @Override
    public <T> List<T> filter(String type,Long mentorId,Long companyId, InternshipStatus status, Class<T> replyClass) {
        InternshipType it = null;

        if (type != null)
            it = InternshipType.valueOf(type);
        if(status==null)
            status=InternshipStatus.PUBLISHED;

        return internshipRepository.filter(it,mentorId, companyId, status)
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
            internship.setStatus(InternshipStatus.PENDING);
        }
        else
        {
            internship.setStatus(InternshipStatus.PUBLISHED);
        }
        internship = internshipRepository.saveAndFlush(internship);
        entityManager.refresh(internship);
        return modelMapper.map(internship, replyClass);

    }

    @Override
    public <T> T update(Long internshipId, InternshipRequest request, Class<T> replyClass) {
        if(!internshipRepository.existsById(internshipId))
            throw new NotFoundException("Data praksa ne postoji");
        Internship existing = internshipRepository.getById(internshipId);
        if(existing.getInternshipType().equals(InternshipType.STRUCNA) && !existing.getStatus().equals(InternshipStatus.DENIED) && !existing.getStatus().equals(InternshipStatus.PENDING))
            throw new ForbiddenException("Ne smijete mjenjati prihvacenu praksu");
        Internship internship = modelMapper.map(request, Internship.class);
        internship.setInternshipId(internshipId);
        if (internship.getSubmissionDue().isAfter(internship.getStartDate()) || internship.getEndDate().isBefore(internship.getStartDate()) || internship.getStartDate().isEqual(internship.getEndDate()))
            throw new BadRequestException("Datumi nisu validni");
        if(InternshipType.STRUCNA.equals(internship.getInternshipType()))
        {
            internship.setStatus(InternshipStatus.PENDING);
        }
        else
        {
            internship.setStatus(InternshipStatus.PUBLISHED);
        }
        internship.setCreatedAt(existing.getCreatedAt());
        internship.setLastModifiedDate(existing.getLastModifiedDate());
        internship = internshipRepository.saveAndFlush(internship);
        entityManager.refresh(internship);
        return modelMapper.map(internship, replyClass);
    }

    @Override
    public <T> T setFinishedStatus(Long internshipId, Class<T> replyClass) {
        if (!internshipRepository.existsById(internshipId)) {
            throw new NotFoundException("Ta praksa ne postoji");
        } else {
            Internship internship = internshipRepository.getById(internshipId);
            internship.setStatus(InternshipStatus.FINISHED);
            internship=internshipRepository.saveAndFlush(internship);
            entityManager.refresh(internship);
            return modelMapper.map(internship, replyClass);
        }
    }

    @Override
    public <T> T setActive(Long internshipId, Class<T> replyClass) {
        if (!internshipRepository.existsById(internshipId)) {
            throw new NotFoundException("Ta praksa ne postoji");
        }
        Internship internship = internshipRepository.getById(internshipId);
       if(internship.getApplications().stream().noneMatch(app -> app.getState().equals(State.PENDING))) {
			internship.setStatus(InternshipStatus.ACTIVE);
			internship = internshipRepository.saveAndFlush(internship);
			entityManager.refresh(internship);
			for (var a : internship.getApplications())
			{
				WorkDairy workDairy = new WorkDairy();
				workDairy = workDiaryRepository.saveAndFlush(workDairy);
				entityManager.refresh(workDairy);
				ReportByMentor report = new ReportByMentor();
				setReportInitialValues(internship, report);
				report = reportByMentorRepository.saveAndFlush(report);
				entityManager.refresh(report);
				StudentHasInternshipPK pks = new StudentHasInternshipPK(a.getId().getStudentId(), internshipId);
				StudentHasInternship shi = new StudentHasInternship();
				shi.setId(pks);
				shi.setInternship(internship);
				shi.setStudent(a.getStudent());
				shi.setWorkDairy(workDairy);
				shi.setReport(report);
				studentHasInternshipRepository.saveAndFlush(shi);
			}
			return modelMapper.map(internship, replyClass);
		}
        else{
			throw new ForbiddenException("Nisu obradjene sve prijave na ovu praksu");
		}
    }

    private void setReportInitialValues(Internship internship, ReportByMentor report)
    {
        report.setOpinionJSON(new OpinionByMentorJSON());
        report.setQuestionnaireJSON(new StudentQuestionnaireJSON());
        report.getOpinionJSON().setMentor(internship.getMentor().getFirstName()+" "+ internship.getMentor().getLastName());
        report.getOpinionJSON().setNumberOfDays(0);
        report.getOpinionJSON().setNumberOfHours(0);
        report.getOpinionJSON().setPeriodOfInternshipFrom(internship.getStartDate());
        report.getOpinionJSON().setPeriodOfInternshipUntil(internship.getEndDate());
        report.getOpinionJSON().setObligations(new ArrayList<>());
        report.getQuestionnaireJSON().setMentorsComment("");
        report.getQuestionnaireJSON().setInput(new ArrayList<>());

        List<ReportByMentorQuestions> questionList = reportByMentorQuestionsRepository.findAll();
        questionList.forEach(reportByMentorQuestion -> {
            var oneEntry = new OneEntryForQuestionnaireJSON();
            oneEntry.setId(reportByMentorQuestion.getId());
            oneEntry.setQuestion(reportByMentorQuestion.getQuestion());
            report.getQuestionnaireJSON().getInput().add(oneEntry);
        });
    }

    @Override
    public <T> T updateReportFromMentor(Long internshipId, Long studentId, ReportByMentorDTO request, Class<T> replyClass) {
        ReportByMentor oldReport = getReportByMentor(studentId, internshipId);
        if (oldReport.getDeletedDate() != null) {
            throw new NotFoundException("Traženi izvještaj mentora je izbrisan!");
        }

        ReportByMentor newReport = modelMapper.map(request, ReportByMentor.class);

        updateReportFields(oldReport, newReport);

        oldReport = reportByMentorRepository.saveAndFlush(oldReport);
        entityManager.refresh(oldReport);
        return modelMapper.map(oldReport, replyClass);
    }

    private void updateReportFields(ReportByMentor oldReport, ReportByMentor newReport)
    {
        oldReport.getOpinionJSON().setPeriodOfInternshipUntil(newReport.getOpinionJSON().getPeriodOfInternshipUntil());
        oldReport.getOpinionJSON().setPeriodOfInternshipFrom(newReport.getOpinionJSON().getPeriodOfInternshipFrom());
        oldReport.getOpinionJSON().setNumberOfHours(newReport.getOpinionJSON().getNumberOfHours());
        oldReport.getOpinionJSON().setNumberOfDays(newReport.getOpinionJSON().getNumberOfDays());
        oldReport.getOpinionJSON().setObligations(newReport.getOpinionJSON().getObligations());
        oldReport.getQuestionnaireJSON().setMentorsComment(newReport.getQuestionnaireJSON().getMentorsComment());

        oldReport.getQuestionnaireJSON().getInput().forEach( entry -> {
            try
            {
                entry.setAnswer(newReport.getQuestionnaireJSON().getEntryById(entry.getId()).getAnswer());
            } catch (NoSuchElementException ignored){ } //ignore if wrong ID is sent by frontend
        });
    }

    @Override
    public void deleteReportFromMentor(Long internshipId, Long studentId)
    {
        ReportByMentor reportByMentor = getReportByMentor(studentId, internshipId);
        reportByMentor.setDeletedDate(LocalDate.now());
        reportByMentorRepository.saveAndFlush(reportByMentor);
        entityManager.refresh(reportByMentor);
    }

    @Override
    public <T> T getReport(Long studentId, Long internshipId, Class<T> replyClass) {
        ReportByMentor reportByMentor = getReportByMentor(studentId, internshipId);
        return modelMapper.map(reportByMentor, replyClass);
    }

    private ReportByMentor getReportByMentor(Long studentId, Long internshipId)
    {
        return reportByMentorRepository.getReport(studentId, internshipId)
                .orElseThrow(()-> new NotFoundException("Nije pronadjen izvjestaj za studenta: " + studentId + ", na praksi: "+ internshipId));
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