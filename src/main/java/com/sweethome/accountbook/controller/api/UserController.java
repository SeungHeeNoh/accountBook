package com.sweethome.accountbook.controller.api;

import com.sweethome.accountbook.common.exception.SystemException;
import com.sweethome.accountbook.domain.User;
import com.sweethome.accountbook.dto.request.UserRequest;
import com.sweethome.accountbook.dto.response.Response;
import com.sweethome.accountbook.dto.response.UserResponse;
import com.sweethome.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController()
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public Map<String, Object> createUser(@RequestBody UserRequest userRequest) {
        Map<String, Object> out = new HashMap<>();
        String result = "fail";
        String msg = "System Error로\n 사용자를 등록하지 못했습니다.";

        try {
            if (userService.createUser(userRequest.toUser()) > 0) {
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

    @GetMapping("/user")
    public Map<String, Object> getUserData(@AuthenticationPrincipal UserDetails userDetails) {
        Map<String, Object> out = new HashMap<>();

        User user = userService.searchUser(userDetails.getUsername());

        out.put("data", UserResponse.from(user));
        return out;
    }

    @PatchMapping("/user/password")
    public Map<String, Object> modifyPassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserRequest userRequest) {
        Map<String, Object> out = new HashMap<>();
        userRequest.setUserId(userDetails.getUsername());
        String result = "fail";
        String msg = "System Error로\n 비밀번호를 수정하지 못했습니다.";

        try {
            if (userService.modifyPassword(userRequest.toUser()) > 0) {
                result = "success";
                msg = "비밀번호를 수정하는 데 성공했습니다.";
            }
        } catch (SystemException e) {
            msg = e.getCode().getMsg();
        }

        Response response = new Response(result, msg);

        out.put("data", response);
        return out;
    }

    @DeleteMapping("/user")
    public Map<String, Object> deleteUser(@AuthenticationPrincipal User user) {
        Map<String, Object> out = new HashMap<>();
        String result = "fail";
        String msg = "System Error로\n 사용자를 삭제하지 못했습니다.";

        try {
            if (userService.deleteUser(user) > 0) {
                result = "success";
                msg = "사용자를 삭제하는 데 성공했습니다.";
            }
        } catch (SystemException e) {
            msg = e.getCode().getMsg();
        }

        Response response = new Response(result, msg);

        out.put("data", response);
        return out;
    }
}

