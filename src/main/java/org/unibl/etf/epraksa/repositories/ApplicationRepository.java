package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.Application;
import org.unibl.etf.epraksa.model.entities.ApplicationPK;
import org.unibl.etf.epraksa.model.entities.Internship;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, ApplicationPK> {

    @Query("SELECT a FROM Application a WHERE" +
            "(a.internship.internshipId = :internshipId) AND" +
            "(a.internship.deletedDate IS NULL)")
    List<Application> filter (Long internshipId);
}
