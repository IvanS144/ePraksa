package org.unibl.etf.epraksa.config;


import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.unibl.etf.epraksa.model.entities.Application;
import org.unibl.etf.epraksa.model.entities.Internship;
import org.unibl.etf.epraksa.model.entities.Student;
import org.unibl.etf.epraksa.model.requests.ApplicationRequest;
import org.unibl.etf.epraksa.repositories.InternshipRepository;
import org.unibl.etf.epraksa.repositories.StudentRepository;

import java.time.LocalTime;

@Component
public class ModelMapperConfig {

    private final InternshipRepository internshipRepository;
    private final StudentRepository studentRepository;



    public ModelMapperConfig(InternshipRepository internshipRepository, StudentRepository studentRepository) {
        this.internshipRepository = internshipRepository;
        this.studentRepository = studentRepository;
    }

    @Bean
    public ModelMapper getModelMapper()
    {
        Converter<String, LocalTime> strToTimeConverter= ctx-> LocalTime.parse(ctx.getSource());
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
        return mapper;
    }
}
