package com.sweethome.accountbook.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
public class AccountLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logSeq;

    @OneToOne
    @JoinColumn(name = "type_id")
    private LogType logType;

    @Column(nullable = false)
    private Long value;
    @Column(nullable = false)
    private LocalDate date;
    @Column
    private String description;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private String createdBy;
    @Column
    private LocalDateTime modifiedAt;
    @Column
    private String modifiedBy;
}
