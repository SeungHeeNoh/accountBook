package com.sweethome.accountbook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;

@ToString
@Getter
@Entity
public class User extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(unique = true, nullable = false)
    private String userId;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "group_seq")
    private UserGroup userGroup;
}
