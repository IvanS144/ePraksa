package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.StudentDTO;
import org.unibl.etf.epraksa.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> getStudents(){
        return studentService.getStudents(StudentDTO.class);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudent(@PathVariable (name = "id") Long studentId){
        return studentService.getStudent(studentId, StudentDTO.class);
    }

    @GetMapping("/practicants")
    public List<StudentDTO> getCurrentPracticants(){
        return studentService.getCurrentPracticants(StudentDTO.class);
    }


}
