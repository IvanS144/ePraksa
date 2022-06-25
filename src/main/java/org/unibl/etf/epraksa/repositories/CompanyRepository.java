package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.epraksa.model.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
