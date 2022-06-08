package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.epraksa.model.dataTransferObjects.NotificationDTO;
import org.unibl.etf.epraksa.services.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userID}")
    public List<NotificationDTO> getNotifications(@PathVariable Integer userID)
    {
        return notificationService.getNotifications(userID, NotificationDTO.class);
    }
}
