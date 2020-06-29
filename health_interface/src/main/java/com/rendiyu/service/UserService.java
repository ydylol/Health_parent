package com.rendiyu.service;

import com.rendiyu.pojo.User;

public interface UserService {
    User findUserByUsername(String username);
}
