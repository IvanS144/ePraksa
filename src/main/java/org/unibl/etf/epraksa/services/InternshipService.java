package org.unibl.etf.epraksa.services;

import java.util.List;
import org.unibl.etf.epraksa.model.requests.InternshipRequest;

public interface InternshipService {

    public void setAcceptanceStatus(Long internshipId, Boolean isAccepted);
    <T> List<T> filter(Long id, String type, Boolean isPublished, Class<T> replyClass);
    <T> T insert(InternshipRequest request, Class<T> replyClass);
    void setFinishedStatus(Long internshipId, Boolean isFinished);
}
