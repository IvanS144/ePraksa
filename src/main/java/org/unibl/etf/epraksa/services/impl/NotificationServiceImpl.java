package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.model.entities.Notification;
import org.unibl.etf.epraksa.repositories.NotificationRepository;
import org.unibl.etf.epraksa.services.NotificationService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private final ModelMapper modelMapper;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(ModelMapper modelMapper, NotificationRepository notificationRepository) {
        this.modelMapper = modelMapper;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void insert(Notification notification) {
        notification.setNotificationID(null);
        notificationRepository.saveAndFlush(notification);
    }

    @Override
    public <T> List<T> getNotifications(Integer userID, Class<T> replyClass) {
        return notificationRepository.getNotificationsByUserID(userID).stream().map(e -> modelMapper.map(e,replyClass)).collect(Collectors.toList());
    }

    public void setDelivered(Long notificationId)
    {
        notificationRepository.setDelivered(notificationId);
    }


}
