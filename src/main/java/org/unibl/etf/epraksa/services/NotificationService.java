package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.entities.Notification;

import java.util.List;

public interface NotificationService {
    void insert(Notification notification);
    <T> List<T> getNotifications(Long userID, Class<T> replyClass);

    void setDelivered(Long notificationId);
}
