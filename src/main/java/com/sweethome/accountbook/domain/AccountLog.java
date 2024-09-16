package com.sweethome.accountbook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate date;
    @Column(length = 50)
    private String description;
}
