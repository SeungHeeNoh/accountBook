package com.sweethome.accountbook.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccountLog {

    @EqualsAndHashCode.Include
    private Long logSeq;

    private LogType logType;

    private Long value;
    private LocalDate date;
    private String description;

    private AuditInfo auditInfo;

    private LogState logState;
}
