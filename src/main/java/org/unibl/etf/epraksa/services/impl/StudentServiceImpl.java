package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.dataTransferObjects.StudentDTO;
import org.unibl.etf.epraksa.model.entities.Student;
import org.unibl.etf.epraksa.repositories.StudentRepository;
import org.unibl.etf.epraksa.services.StudentService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public <T> List<T> getStudents(Class<T> replyClass) {
//        List<T> students = new ArrayList<>();

//        if(studentID!=null){
//            Student s = studentRepository.findById(studentID)
//                    .orElseThrow(()-> new NotFoundException("Nema takvog studenta"));
//            students.add(modelMapper.map(s, replyClass));
//            return students;
//        }
        return studentRepository.findAll()
                .stream()
                .map(e-> modelMapper.map(e,replyClass))
                .collect(Collectors.toList());

//        return studentRepository.getCurrentPracticants()
//                .stream()
//                .map(e-> modelMapper.map(e, replyClass))
//                .collect(Collectors.toList());
    }

    @Override
    public <T> T getStudent(Long studentId, Class<T> replyClass) {
        return modelMapper.map(studentRepository.
                findById(studentId)
                .orElseThrow(()-> new NotFoundException("Nema takvog studenta")), replyClass);
    }

    @Override
    public <T> List<T> getCurrentPracticants(Class<T> replyClass) {
        return studentRepository.getCurrentPracticants()
                .stream()
                .map(e-> modelMapper.map(e, replyClass))
                .collect(Collectors.toList());
    }
}
