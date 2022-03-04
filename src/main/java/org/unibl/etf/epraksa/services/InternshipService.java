package org.unibl.etf.epraksa.services;

import java.util.List;

public interface InternshipService {

    public void setAcceptanceStatus(Long internshipId, Boolean isAccepted);
    <T> List<T> filter(Long id, String type, Boolean isPublished, Class<T> replyClass);
}
