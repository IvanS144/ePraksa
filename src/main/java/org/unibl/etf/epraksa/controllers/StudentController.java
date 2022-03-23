package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public List<StudentDTO> getStudents(@RequestParam (name = "id", required = false) Long studentId,
                                        @RequestParam (name = "isPracticant", required = false) Boolean isPracticant){
        return studentService.getStudents(studentId, isPracticant, StudentDTO.class);
    }
}
