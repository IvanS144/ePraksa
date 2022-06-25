package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.dataTransferObjects.ReportByMentorDTO;
import org.unibl.etf.epraksa.model.entities.Internship;
import org.unibl.etf.epraksa.model.entities.ReportByMentor;
import org.unibl.etf.epraksa.model.entities.ReportByMentorQuestions;
import org.unibl.etf.epraksa.model.entities.json.OneEntryForQuestionnaireJSON;
import org.unibl.etf.epraksa.model.entities.json.OpinionByMentorJSON;
import org.unibl.etf.epraksa.model.entities.json.StudentQuestionnaireJSON;
import org.unibl.etf.epraksa.repositories.ReportByMentorQuestionsRepository;
import org.unibl.etf.epraksa.repositories.ReportByMentorRepository;
import org.unibl.etf.epraksa.services.ReportByMentorService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportByMentorServiceImpl implements ReportByMentorService
{
    private final ModelMapper modelMapper;
    private final ReportByMentorRepository reportByMentorRepository;
    private final ReportByMentorQuestionsRepository reportByMentorQuestionsRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public ReportByMentorServiceImpl(ModelMapper modelMapper, ReportByMentorRepository reportByMentorRepository, ReportByMentorQuestionsRepository reportByMentorQuestionsRepository, EntityManager entityManager)
    {
        this.modelMapper = modelMapper;
        this.reportByMentorRepository = reportByMentorRepository;
        this.reportByMentorQuestionsRepository = reportByMentorQuestionsRepository;
        this.entityManager = entityManager;
    }

    private ReportByMentor getReportByMentor(Long studentId, Long internshipId)
    {
        return reportByMentorRepository.getReport(studentId, internshipId)
                .orElseThrow(()-> new NotFoundException("Nije pronadjen izvjestaj za studenta: " + studentId + ", na praksi: "+ internshipId));
    }

    @Override
    public <T> T getReport(Long studentId, Long internshipId, Class<T> replyClass) {
        ReportByMentor reportByMentor = getReportByMentor(studentId, internshipId);
        return modelMapper.map(reportByMentor, replyClass);
    }
    @Override
    public void setReportInitialValues(Internship internship, ReportByMentor report)
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
    public <T> T getLatestReport(Long studentId, Class<T> replyClass) {
        List<ReportByMentor> reports = reportByMentorRepository.getLatestReport(studentId);
        if(reports.isEmpty())
            throw new NotFoundException("Student još nema nijedan izvještaj");
        return modelMapper.map(reports.get(0), replyClass );
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

    @Override
    public void deleteReportFromMentor(Long internshipId, Long studentId)
    {
        ReportByMentor reportByMentor = getReportByMentor(studentId, internshipId);
        reportByMentor.setDeletedDate(LocalDate.now());
        reportByMentorRepository.saveAndFlush(reportByMentor);
        entityManager.refresh(reportByMentor);
    }

    @Override
    public List<ReportByMentorQuestions> getAllQuestions()
    {
        return reportByMentorQuestionsRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(ReportByMentorQuestions::getId))
                .collect(Collectors.toList());
    }
}
