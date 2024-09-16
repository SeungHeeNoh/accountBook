package com.sweethome.accountbook.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
public class LogType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;

    @Column(nullable = false)
    private String typeName;
    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isDeposit;
    @Column
    private String description;
    @Column(nullable = false)
    private Long parentTypeId;

    @ManyToOne
    @JoinColumn(name = "group_seq")
    private UserGroup userGroup;

    @OneToMany
    @JoinColumn(name="type_id")
    private List<LogType> childTypes;
}
