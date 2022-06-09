package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationDTO {
    private Long notificationID;
    private String subject;
    private String text;
    private LocalDate createdAt;
}
