package com.sweethome.accountbook.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long userSeq;
    private String userId;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long groupSeq;
}
