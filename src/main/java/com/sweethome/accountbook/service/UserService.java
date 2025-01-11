package com.sweethome.accountbook.service;

import com.sweethome.accountbook.domain.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    User searchUser(String userId) throws UsernameNotFoundException;

    int updateLastLoginAt(User user);

    int createUser(User user);
}
