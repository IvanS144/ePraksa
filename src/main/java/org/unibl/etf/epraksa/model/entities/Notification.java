package org.unibl.etf.epraksa.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="NotificationID")
    private Integer notificationID;
    @Basic
    @Column(name="Subject")
    private String subject;
    @Basic
    @Column(name="Text")
    private String text;
    @Basic
    @Column(name="Delivered")
    private Boolean delivered = false;
    @CreatedDate
    @Column(name = "CreatedAt",
            nullable = false)
    private LocalDate createdAt;
    @Column(name = "DeletedDate")
    private LocalDate deletedDate;
    @Basic
    @Column(name="UserID")
    private Long userID;
}
