package com.sweethome.accountbook.controller.api;

import com.sweethome.accountbook.common.exception.SystemException;
import com.sweethome.accountbook.dto.request.UserRequest;
import com.sweethome.accountbook.dto.response.Response;
import com.sweethome.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController()
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public Map<String, Object> createUser(@RequestBody UserRequest userRequest) {
        Map<String, Object> out = new HashMap<>();
        String result = "fail";
        String msg = "System Error로\n 사용자를 등록하지 못했습니다.";
        int insertResult = userService.createUser(userRequest.toUser());

        try {
            if (insertResult > 0) {
                result = "success";
                msg = "사용자를 등록하는 데 성공했습니다.";
            }
        } catch (SystemException e) {
            msg = e.getCode().getMsg();
        }

        Response response = new Response(result, msg);

        out.put("data", response);
        return out;
    }
}

