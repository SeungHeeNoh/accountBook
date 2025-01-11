package com.sweethome.accountbook.service;

import com.sweethome.accountbook.domain.User;
import com.sweethome.accountbook.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public User searchUser(String userId) throws UsernameNotFoundException {
        return userMapper.findByUserId(userId);
    }
}
