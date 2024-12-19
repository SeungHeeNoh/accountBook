package com.sweethome.accountbook.mapper;


import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.dto.LogTypeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogTypeMapper {

    List<LogTypeDto> findByParam(LogType logType);

    int getDuplicateTypeNameCount(LogType logType);

    LogTypeDto findByTypeId(LogType logType);

    int insert(LogType logType);

    int update(LogType logType);

    int delete(LogType logType);
}
