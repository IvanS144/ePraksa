package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.BadRequestException;
import org.unibl.etf.epraksa.exceptions.ForbiddenException;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.entities.*;
import org.unibl.etf.epraksa.model.requests.InternshipRequest;
import org.unibl.etf.epraksa.repositories.*;
import org.unibl.etf.epraksa.services.InternshipService;
import org.unibl.etf.epraksa.services.ReportByMentorService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final StudentHasInternshipRepository studentHasInternshipRepository;
    private final ModelMapper modelMapper;
    private final ReportByMentorRepository reportByMentorRepository;
    private final ReportByMentorService reportByMentorService;
    @PersistenceContext
    private EntityManager entityManager;
    private final WorkDiaryRepository workDiaryRepository;
    private final NotificationRepository notificationRepository;
    private final CommissionMemberRepository commissionMemberRepository;

    public InternshipServiceImpl(InternshipRepository internshipRepository, StudentHasInternshipRepository studentHasInternshipRepository,
                                 ModelMapper modelMapper, ReportByMentorRepository reportByMentorRepository, ReportByMentorService reportByMentorService,
                                 EntityManager entityManager, WorkDiaryRepository workDiaryRepository, NotificationRepository notificationRepository,
                                 CommissionMemberRepository commissionMemberRepository) {
        this.internshipRepository = internshipRepository;
        this.studentHasInternshipRepository = studentHasInternshipRepository;
        this.modelMapper = modelMapper;
        this.reportByMentorRepository = reportByMentorRepository;
        this.reportByMentorService = reportByMentorService;
        this.entityManager = entityManager;
        this.workDiaryRepository = workDiaryRepository;
        this.notificationRepository = notificationRepository;
        this.commissionMemberRepository = commissionMemberRepository;
    }

    @Override
    public void setAcceptanceStatus(Long internshipId, Boolean flag, Comment reason) {
        if (!internshipRepository.existsById(internshipId)) {
            throw new NotFoundException("Ta praksa ne postoji");
        } else {
            Internship internship = internshipRepository.getById(internshipId);
            internship.setStatus(flag? InternshipStatus.PUBLISHED : InternshipStatus.DENIED);

            internshipRepository.saveAndFlush(internship);

            if(flag==true)
            {
                Notification n = Notification.builder().subject("Odobrenje prakse").text("Obajvještavamo Vas da je praksa "+internship.getTitle()+ "odobrena.").userID(internship.getCompany().getId()).delivered(false).build();
                notificationRepository.saveAndFlush(n);
            }
            else
            {
                Notification n = Notification.builder().subject("Odbijanje prakse").text("Obajvještavamo Vas da je praksa "+internship.getTitle()+ "odbijena.\n Razlog:\n"+reason.getComment()).userID(internship.getCompany().getId()).delivered(false).build();
                notificationRepository.saveAndFlush(n);
            }
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
        Internship finalInternship = internship;
        commissionMemberRepository.findAll().stream().filter(CommissionMember::getIsCurrentMember).forEach(c ->{
            Notification nc = Notification.builder().subject("Zahtjev za stručnu praksu").text("Obajvještavamo Vas da je kompanija" +finalInternship.getCompany().getName() +" poslala zahtjev za odobrenje stručne prakse: " + finalInternship.getTitle()).userID(c.getId()).delivered(false).build();
            notificationRepository.saveAndFlush(nc);});
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
            Notification n = Notification.builder().subject("Kraj prakse").text("Obajvjestavamo Vas da je praksa "+internship.getTitle()+ "zavrsena.").userID(internship.getCompany().getId()).delivered(false).build();
            notificationRepository.saveAndFlush(n);
            Internship finalInternship = internship;
            commissionMemberRepository.findAll().stream().filter(CommissionMember::getIsCurrentMember).forEach(c ->{
                Notification nc = Notification.builder().subject("Kraj prakse").text("Obajvjestavamo Vas da je praksa "+ finalInternship.getTitle()+ "zavrsena.").userID(c.getId()).delivered(false).build();
                notificationRepository.saveAndFlush(nc);});
            studentHasInternshipRepository.getAllStudentsOnInternship(internship.getInternshipId()).stream().forEach(s -> {
                Notification ns = Notification.builder().subject("Kraj prakse").text("Obajvjestavamo Vas da je praksa "+ finalInternship.getTitle()+ "zavrsena.").userID(s.getId()).delivered(false).build();
                notificationRepository.saveAndFlush(ns);
            });
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
                workDairy.setState(State.PENDING);
				workDairy = workDiaryRepository.saveAndFlush(workDairy);
				entityManager.refresh(workDairy);
				ReportByMentor report = new ReportByMentor();
				reportByMentorService.setReportInitialValues(internship, report);
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
           Notification n = Notification.builder().subject("Pocetak prakse").text("Obajvjestavamo Vas da je praksa "+internship.getTitle()+ "zapocela.").userID(internship.getCompany().getId()).delivered(false).build();
           notificationRepository.saveAndFlush(n);
           Internship finalInternship = internship;
           commissionMemberRepository.findAll().stream().filter(CommissionMember::getIsCurrentMember).forEach(c ->{
           Notification nc = Notification.builder().subject("Pocetak prakse").text("Obajvjestavamo Vas da je praksa "+ finalInternship.getTitle()+ "zapocela.").userID(c.getId()).delivered(false).build();
           notificationRepository.saveAndFlush(nc);});
           studentHasInternshipRepository.getAllStudentsOnInternship(internship.getInternshipId()).stream().forEach(s -> {
               Notification ns = Notification.builder().subject("Pocetak prakse").text("Obajvjestavamo Vas da je praksa "+ finalInternship.getTitle()+ "zapocela.").userID(s.getId()).delivered(false).build();
               notificationRepository.saveAndFlush(ns);
           });
			return modelMapper.map(internship, replyClass);
		}
        else{
			throw new ForbiddenException("Nisu obradjene sve prijave na ovu praksu");
		}
    }

    public <T> List<T> getAllStudentsOnInternship(Long internshipId, Class<T> replyClass) {
        return studentHasInternshipRepository.getAllStudentsOnInternship(internshipId)
                .stream()
                .map(s -> modelMapper.map(s, replyClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> T getInternship(Long internshipId, Class<T> replyCLass) {
        return modelMapper.map(internshipRepository
                .findById(internshipId)
                .orElseThrow(()-> new NotFoundException("Nije pronadjena praksa: " + internshipId)), replyCLass);
    }

}