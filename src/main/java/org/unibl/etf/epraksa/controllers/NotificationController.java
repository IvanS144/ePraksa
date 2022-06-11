package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.NotificationDTO;
import org.unibl.etf.epraksa.services.NotificationService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("user/{userID}")
    public List<NotificationDTO> getNotifications(@PathVariable Long userID)
    {
        return notificationService.getNotifications(userID, NotificationDTO.class);
    }

    @PatchMapping("/{notificationId}")
    public void setSeen(@PathVariable Long notificationId)
    {
        notificationService.setDelivered(notificationId);
    }
}
