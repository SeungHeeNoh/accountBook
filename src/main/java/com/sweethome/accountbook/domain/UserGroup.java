package com.sweethome.accountbook.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Entity
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupSeq;

    @Column(nullable = false)
    private String groupName;
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
