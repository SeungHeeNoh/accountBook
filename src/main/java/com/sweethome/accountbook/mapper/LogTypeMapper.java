package com.sweethome.accountbook.mapper;


import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.dto.LogTypeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogTypeMapper {

    List<LogTypeDto> findByParam(LogType param);

    int getDuplicateTypeNameCount(LogType param);

    LogTypeDto findByTypeId(LogType param);

    int insert(LogType param);

    int update(LogType param);

    int delete(LogType param);
}
