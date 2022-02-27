package org.unibl.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AddressID",
            nullable = false)
    private Long addressId;

    @Basic
    @Column(name = "City",
            nullable = false,
            length = 45)
    private String city;

    @Basic
    @Column(name = "Street",
            nullable = false,
            length = 45)
    private String street;

    @Basic
    @Column(name = "Number",
            nullable = false,
            length = 45)
    private String number;

    @Basic
    @Column(name = "PostalCode",
            nullable = false,
            length = 45)
    private String postalCode;

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

    @OneToMany(mappedBy = "address",
            fetch = FetchType.LAZY)
    private List<User> users;

}
