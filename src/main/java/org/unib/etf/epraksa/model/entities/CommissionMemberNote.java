package org.unib.etf.epraksa.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "commission_member_note")
public class CommissionMemberNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommisionMemberNoteID",
            nullable = false)
    private Long commissionMemberNoteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CommissionMemberID",
            referencedColumnName = "ID",
            nullable = false)
    private CommissionMember commissionMember;

    @Basic
    @Column(name = "Text")
    private String text;

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
