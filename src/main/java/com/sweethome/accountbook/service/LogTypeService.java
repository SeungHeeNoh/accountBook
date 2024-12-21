package com.sweethome.accountbook.service;

import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.dto.LogTypeDto;

import java.util.List;

public interface LogTypeService {

    List<LogTypeDto> searchLogTypes(LogType logType);

    int createLogType(LogType requestParam);

    LogTypeDto searchLogType(LogType requestParam);

    int updateLogType(LogType requestParam);
}
