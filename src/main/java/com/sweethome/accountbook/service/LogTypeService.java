package com.sweethome.accountbook.service;

import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.dto.LogTypeDto;

import java.util.List;

public interface LogTypeService {

    List<LogTypeDto> searchLogTypes(LogType param);

    int createLogType(LogType param);

    LogTypeDto searchLogType(LogType param);

    int updateLogType(LogType param);

    int deleteLogType(LogType param);

    List<LogTypeDto> searchSubLogType(LogType param);
}
