package com.sweethome.accountbook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Entity
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupSeq;

    @Column(nullable = false, length = 10)
    private String groupName;
    @Column(length = 50)
    private String description;
}
