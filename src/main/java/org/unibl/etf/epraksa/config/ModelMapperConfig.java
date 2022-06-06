package org.unibl.etf.epraksa.config;


import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.unibl.etf.epraksa.model.dataTransferObjects.InternshipApplicationDTO;
import org.unibl.etf.epraksa.model.dataTransferObjects.StudentApplicationDTO;
import org.unibl.etf.epraksa.model.entities.Application;
import org.unibl.etf.epraksa.model.entities.Internship;
import org.unibl.etf.epraksa.model.entities.Student;
import org.unibl.etf.epraksa.model.entities.*;
import org.unibl.etf.epraksa.model.replies.WorkDairyReply;
import org.unibl.etf.epraksa.model.requests.ApplicationRequest;
import org.unibl.etf.epraksa.model.requests.WorkDiaryEntryRequest;
import org.unibl.etf.epraksa.repositories.InternshipRepository;
import org.unibl.etf.epraksa.repositories.StudentRepository;
import org.unibl.etf.epraksa.repositories.WorkDiaryRepository;

import java.time.Duration;
import java.time.LocalTime;

@Component
public class ModelMapperConfig {

    private final InternshipRepository internshipRepository;
    private final StudentRepository studentRepository;
    private final WorkDiaryRepository workDiaryRepository;



    public ModelMapperConfig(InternshipRepository internshipRepository, StudentRepository studentRepository, WorkDiaryRepository workDiaryRepository) {
        this.internshipRepository = internshipRepository;
        this.studentRepository = studentRepository;
        this.workDiaryRepository = workDiaryRepository;
    }

    @Bean
    public ModelMapper getModelMapper()
    {
        Converter<String, LocalTime> strToTimeConverter= ctx-> LocalTime.parse(ctx.getSource());
        Converter<Long, WorkDairy> longToWorkDairyConverter = ctx-> workDiaryRepository.getById(ctx.getSource());
        Converter<Long, Internship> longToInternshipConverter = ctx -> internshipRepository.getById(ctx.getSource());
        Converter<Long, Student> longToStudentConverter = ctx -> studentRepository.getById(ctx.getSource());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
//        mapper.createTypeMap(WorkDiaryEntryRequest.class, WorkDairyEntry.class)
//                .addMappings(map -> map.using(strToTimeConverter).map(WorkDiaryEntryRequest::getFromTime, WorkDairyEntry::setFromTime))
//                .addMappings(map -> map.using(strToTimeConverter)
//                        .map(WorkDiaryEntryRequest::getFromTime, WorkDairyEntry::setFromTime));
        mapper.createTypeMap(ApplicationRequest.class, Application.class)
                .addMappings(map -> map.using(longToInternshipConverter).map(ApplicationRequest::getInternshipId, Application::setInternship))
                .addMappings(map -> map.using(longToStudentConverter).map(ApplicationRequest::getStudentId, Application::setStudent));

        mapper.createTypeMap(Application.class, StudentApplicationDTO.class)
                .setPostConverter(ctx -> {
                    Application source = ctx.getSource();
                    StudentApplicationDTO dest = ctx.getDestination();
                    dest.setCompanyName(source.getInternship().getCompany().getName());
                    dest.setInternshipName(source.getInternship().getTitle());
                    dest.setInternshipId(source.getId().getInternshipId());
                    dest.setStudentId(source.getId().getStudentId());
                    dest.setState(dest.getState());
                    dest.setMotivationalLetter(source.getMotivationalLetter());
                    dest.setInternshipType(source.getInternship().getInternshipType());
                    return dest;
                });

        mapper.createTypeMap(Application.class, InternshipApplicationDTO.class)
                .setPostConverter(ctx -> {
                    Application source = ctx.getSource();
                    InternshipApplicationDTO dest = ctx.getDestination();
                    dest.setStudentId(source.getId().getStudentId());
                    dest.setInternshipId(source.getId().getInternshipId());
                    dest.setStudentFirstName(source.getStudent().getFirstName());
                    dest.setStudentLastName(source.getStudent().getLastName());
                    dest.setState(dest.getState());
                    dest.setMotivationalLetter(source.getMotivationalLetter());
                    dest.setInternshipType(source.getInternship().getInternshipType());
                    return dest;
                });

        mapper.createTypeMap(WorkDairy.class, WorkDairyReply.class)
                .setPostConverter(ctx->{
                    WorkDairyReply reply = ctx.getDestination();
                    WorkDairy dairy = ctx.getSource();
                    reply.setStudentFullName(dairy.getStudentOnInternship().getStudent().getFirstName()+" "+ dairy.getStudentOnInternship().getStudent().getLastName());
                    reply.setMentorFullName(dairy.getStudentOnInternship().getInternship().getMentor().getFirstName()+ " " + dairy.getStudentOnInternship().getInternship().getMentor().getLastName());
                    reply.setStudentIndex(dairy.getStudentOnInternship().getStudent().getIndex());
                    reply.setStudentCourse(dairy.getStudentOnInternship().getStudent().getCourse());
                    try {
                        Integer sum= dairy.getWorkDairyEntries().stream().map(e -> (int) Duration.between(e.getFromTime(), e.getToTime()).toHours()).reduce(0, (a, b) -> a + b);
                        reply.setWorkedHours(sum);
                    }
                    catch(Exception e)
                    {
                        reply.setWorkedHours(0);
                    }
                    return reply;
                });

        mapper.createTypeMap(WorkDiaryEntryRequest.class, WorkDairyEntry.class)
                .addMappings(map-> map.using(longToWorkDairyConverter).map(WorkDiaryEntryRequest::getWorkDiaryId, WorkDairyEntry::setWorkDairy));
        return mapper;
    }
}
