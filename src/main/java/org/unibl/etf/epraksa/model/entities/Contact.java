package org.unibl.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContactID",
            nullable = false)
    private Long contactId;

    @Basic
    @Column(name = "Number",
            nullable = false,
            length = 45)
    private String number;

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

    @Basic
    @Column(name = "CreatedAt",
            nullable = false)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "LastModifiedDate",
            nullable = false)
    private LocalDateTime lastModifiedDate;

    @Basic
    @Column(name = "DeletedDate")
    private LocalDateTime deletedDate;

}
