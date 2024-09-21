package com.sweethome.accountbook.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountLog extends BaseDomain {

    private Long logSeq;

    private LogType logType;

    private Long value;
    private LocalDate date;
    private String description;
}
