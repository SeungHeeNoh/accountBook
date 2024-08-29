package com.sweethome.accountbook.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AccountBook {
    private Long logSeq;
    private LogType logType;
    private Long value;
    private LocalDate date;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
