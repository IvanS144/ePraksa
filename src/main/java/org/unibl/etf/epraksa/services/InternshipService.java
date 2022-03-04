package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.requests.InternshipRequest;

public interface InternshipService {

    void setAcceptanceStatus(Long internshipId, Boolean isAccepted);
    <T> T insert(InternshipRequest request, Class<T> replyClass);
    void setFinishedStatus(Long internshipId, Boolean isFinished);
}
