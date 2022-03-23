package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;
import org.unibl.etf.epraksa.model.entities.Address;
import org.unibl.etf.epraksa.model.entities.Contact;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

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

    private List<ContactDTO> contacts;

    private AddressDTO address;
}
