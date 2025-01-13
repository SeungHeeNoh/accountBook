package com.sweethome.accountbook.service;

import com.sweethome.accountbook.common.Code;
import com.sweethome.accountbook.common.exception.SystemException;
import com.sweethome.accountbook.domain.User;
import com.sweethome.accountbook.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public User searchUser(String userId) throws UsernameNotFoundException {
        return userMapper.findByUserId(userId);
    }

    @Override
    public int updateLastLoginAt(User user) {
        return userMapper.updateLastLoginAt(user);
    }

    @Override
    public int createUser(User user) {
        if(userMapper.getDuplicateUserIdCount(user.getUserId()) > 0) {
            throw new SystemException(Code.DUPLICATE_USERID_EXIST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.insertUser(user);
    }
}
