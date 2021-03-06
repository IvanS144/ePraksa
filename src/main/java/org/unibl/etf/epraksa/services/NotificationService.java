package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.entities.Notification;

import java.util.List;

public interface NotificationService {
    void insert(Notification notification);
    <T> List<T> getNotifications(Integer userID, Class<T> replyClass);
}
