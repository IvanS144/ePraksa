package org.unibl.etf.epraksa.services;

public interface InternshipService {

    void setAcceptanceStatus(Long internshipId, Boolean isAccepted);
    void setFinishedStatus(Long internshipId, Boolean isFinished);
}
