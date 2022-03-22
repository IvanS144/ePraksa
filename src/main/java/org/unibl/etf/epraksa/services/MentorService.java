package org.unibl.etf.epraksa.services;
import java.util.List;

public interface MentorService {
    <T> List<T> getAllMentors(Long companyId,Class<T> replyClass);
}
