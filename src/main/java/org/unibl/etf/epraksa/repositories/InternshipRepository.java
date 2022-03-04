package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.epraksa.model.entities.Internship;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
}
