package com.sweethome.accountbook.controller;

import com.sweethome.accountbook.common.exception.SystemException;
import com.sweethome.accountbook.domain.AuditInfo;
import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.domain.UserGroup;
import com.sweethome.accountbook.dto.request.LogTypeManageRequest;
import com.sweethome.accountbook.dto.request.LogTypeSearchRequest;
import com.sweethome.accountbook.dto.response.LogTypeResponse;
import com.sweethome.accountbook.dto.response.Response;
import com.sweethome.accountbook.service.LogTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class LogTypeController {

    private final LogTypeService logTypeService;

    @GetMapping("/log-types")
    public Map<String, Object> searchLogTypes(LogTypeSearchRequest logTypeRequest, UserGroup userGroup) {
        Map<String, Object> out = new HashMap<>();
        LogType requestParam = logTypeRequest.toLogType();
        // to-do : remove
        userGroup.setGroupSeq(1L);
        requestParam.setUserGroup(userGroup);

        List<LogTypeResponse> searchResult = logTypeService.searchLogTypes(requestParam)
                .stream().map(LogTypeResponse::from).toList();

        out.put("data", searchResult);
        return out;
    }

    @PostMapping("/log-type")
    public Map<String, Object> createLogType(LogTypeManageRequest logTypeRequest, UserGroup userGroup) {
        Map<String, Object> out = new HashMap<>();
        String result = "fail";
        String msg = "System Error로\n 가계부 항목을 등록하지 못했습니다.";

        LogType requestParam = logTypeRequest.toLogType();
        // to-do : remove
        userGroup.setGroupSeq(1L);
        requestParam.setUserGroup(userGroup);
        requestParam.setAuditInfo(AuditInfo.builder().createdBy("nsh").build());

        try {
            int insertResult = logTypeService.createLogType(requestParam);

            if (insertResult > 0) {
                result = "success";
                msg = "가계부 항목을 추가하는 데 성공했습니다.";
            }
        } catch (SystemException e) {
            msg = e.getCode().getMsg();
        }

        Response response = new Response(result, msg);

        out.put("data", response);
        return out;
    }
}
