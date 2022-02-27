package org.unib.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@Inheritance(strategy=InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",
            nullable = false)
    private Long Id;

    @Basic
    @Column(name = "Loginmail",
            nullable = false,
            length = 45)
    private String loginMail;

    @Basic
    @Column(name = "IsActive",
            nullable = false)
    private Boolean isActive;

    @Basic
    @Column(name = "Password",
            nullable = false,
            length = 45)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE",
            nullable = false,
            length = 45)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "USERTYPE",
            nullable = false,
            length = 45)
    private UserType userType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AddressID",
            referencedColumnName = "AddressID",
            nullable = false)
    private Address address;

    @Basic
    @Column(name = "DeletedDate")
    private LocalDateTime deletedDate;

    @Basic
    @Column(name = "CreatedAt",
            nullable = false)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "LastModifiedDate",
            nullable = false)
    private LocalDateTime lastModifiedDate;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY)
    private List<Contact> contacts;


}
