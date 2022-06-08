package org.unibl.etf.epraksa.model.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmailID",
            nullable = false)
    private Long contactId;

    @Basic
    @Column(name = "Mail",
            nullable = false,
            length = 45)
    private String mail;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID",
            referencedColumnName = "ID",
            nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "CreatedAt",
            nullable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "LastModifiedDate",
            nullable = false)
    private LocalDate lastModifiedDate;

    @Basic
    @Column(name = "DeletedDate")
    private LocalDate deletedDate;

}
