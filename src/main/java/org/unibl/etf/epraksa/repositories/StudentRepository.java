package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT shi.student FROM StudentHasInternship shi " +
            "WHERE shi.internship.status=org.unibl.etf.epraksa.model.entities.InternshipStatus.ACTIVE")
    List<Student> getCurrentPracticants();
}
