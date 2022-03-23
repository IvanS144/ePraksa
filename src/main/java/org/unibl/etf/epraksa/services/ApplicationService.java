package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.entities.State;
import org.unibl.etf.epraksa.model.requests.ApplicationRequest;

public interface ApplicationService {

    void insert(ApplicationRequest request);
    void setState(Long internshipId, Long studentId, State state);
}
