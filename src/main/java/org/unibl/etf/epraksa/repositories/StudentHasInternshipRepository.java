package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.epraksa.model.entities.Student;
import org.unibl.etf.epraksa.model.entities.StudentHasInternship;
import org.unibl.etf.epraksa.model.entities.StudentHasInternshipPK;

import java.util.List;

public interface StudentHasInternshipRepository extends JpaRepository<StudentHasInternship, StudentHasInternshipPK> {
    @Query("SELECT shi.student FROM StudentHasInternship shi WHERE shi.id.id= :id")
    List<Student> getAllStudentsOnInternship(@Param("id") Long id);
}
