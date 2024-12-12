package com.sweethome.accountbook.dto;

import com.sweethome.accountbook.domain.AccountLog;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountLogDto extends AccountLog {
    LocalDate startDate;
    LocalDate endDate;
}
