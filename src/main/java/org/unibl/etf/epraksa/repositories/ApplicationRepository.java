package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.epraksa.model.entities.Application;
import org.unibl.etf.epraksa.model.entities.ApplicationPK;

public interface ApplicationRepository extends JpaRepository<Application, ApplicationPK> {
}
