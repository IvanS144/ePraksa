package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class StudentDTO {

    private Long id;
    private String firstName;

    private String lastName;

    private String jmbg;

    private String index;

    private LocalDate birthDate;

    private String faculty;

    private Integer year;

    private String course;

    private String cycle;

    private CvDTO cv;
}
