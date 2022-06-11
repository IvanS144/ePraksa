package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    List<Notification> getNotificationsByUserID(Long userID);
    @Query("UPDATE Notification n SET n.delivered=true WHERE n.notificationID= :notificationId")
    void setDelivered(Long notificationId);
}
