package com.rendiyu.dao;

import com.rendiyu.pojo.User;

public interface UserDao {

    User findUserByUsername(String username);
}
