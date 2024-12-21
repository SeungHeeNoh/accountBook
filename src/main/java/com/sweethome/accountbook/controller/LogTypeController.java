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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class LogTypeController {

    private final LogTypeService logTypeService;

    @GetMapping("/log-types")
    public Map<String, Object> searchLogTypes(LogTypeSearchRequest logTypeSearchRequest, UserGroup userGroup) {
        Map<String, Object> out = new HashMap<>();
        LogType requestParam = logTypeSearchRequest.toLogType();
        // to-do : remove
        userGroup.setGroupSeq(1L);
        requestParam.setUserGroup(userGroup);

        List<LogTypeResponse> searchResult = logTypeService.searchLogTypes(requestParam)
                .stream().map(LogTypeResponse::from).toList();

        out.put("data", searchResult);
        return out;
    }

    @PostMapping("/log-type")
    public Map<String, Object> createLogType(@RequestBody LogTypeManageRequest logTypeManageRequest, UserGroup userGroup) {
        Map<String, Object> out = new HashMap<>();
        String result = "fail";
        String msg = "System Error로\n 가계부 항목을 등록하지 못했습니다.";

        LogType requestParam = logTypeManageRequest.toLogType();
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

    @GetMapping("/log-type/{typeId}")
    public Map<String, Object> getLogTypeData(@PathVariable Long typeId, UserGroup userGroup) {
        Map<String, Object> out = new HashMap<>();
        LogType requestParam = LogType.builder()
                .typeId(typeId)
                .build();

        // to-do : remove
        userGroup.setGroupSeq(1L);
        requestParam.setUserGroup(userGroup);

        LogTypeResponse searchResult = LogTypeResponse.from(logTypeService.searchLogType(requestParam));

        out.put("data", searchResult);
        return out;
    }

    @PutMapping("/log-type/{typeId}")
    public Map<String, Object> modifyLogType(@PathVariable Long typeId, @RequestBody LogTypeManageRequest logTypeManageRequest, UserGroup userGroup) {
        Map<String, Object> out = new HashMap<>();
        String result = "fail";
        String msg = "System Error로\n 가계부 항목을 수정하지 못했습니다.";

        LogType requestParam = logTypeManageRequest.toLogType();
        requestParam.setTypeId(typeId);

        // to-do : remove
        userGroup.setGroupSeq(1L);
        requestParam.setUserGroup(userGroup);
        requestParam.setAuditInfo(AuditInfo.builder().modifiedBy("nsh").build());

        try {
            int updateResult = logTypeService.updateLogType(requestParam);

            if (updateResult > 0) {
                result = "success";
                msg = "가계부 항목을 수정하는 데 성공했습니다.";
            }
        } catch (SystemException e) {
            msg = e.getCode().getMsg();
        }

        Response response = new Response(result, msg);

        out.put("data", response);
        return out;
    }
}
