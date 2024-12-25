package com.sweethome.accountbook.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteLogTypeDto {
    private List<Long> typeIds;
    private long groupSeq;

    public static DeleteLogTypeDto create(List<Long> logTypeIds, long logGroupId) {
        DeleteLogTypeDto dto = new DeleteLogTypeDto();

        dto.setTypeIds(logTypeIds);
        dto.setGroupSeq(logGroupId);

        return dto;
    }
}
