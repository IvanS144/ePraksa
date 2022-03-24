package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDTO extends UserDTO{

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

    private NumberDTO number;

    private EmailDTO email;

    private AddressDTO address;
}
